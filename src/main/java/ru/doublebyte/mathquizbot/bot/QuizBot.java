package ru.doublebyte.mathquizbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.doublebyte.mathquizbot.bot.types.*;
import ru.doublebyte.mathquizbot.bot.types.mediaentity.Chat;
import ru.doublebyte.mathquizbot.bot.types.util.EditMessageTextParams;
import ru.doublebyte.mathquizbot.bot.types.util.SendMessageParams;
import ru.doublebyte.mathquizbot.quiz.Level;
import ru.doublebyte.mathquizbot.quiz.Quiz;
import ru.doublebyte.mathquizbot.quiz.QuizVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Math quiz bot
 */
public class QuizBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(QuizBot.class);

    private Map<Long, Quiz> quizCollection = new ConcurrentHashMap<>();
    private AtomicLong quizIdSequence = new AtomicLong(0);

    public QuizBot(String apiUrl, String token) {
        super(apiUrl, token);
    }


    /**
     * Process bot update
     * @param update Update object
     */
    @Override
    protected void processUpdate(Update update) {
        if(update.getMessage() != null) {
            Message messageObj = update.getMessage();
            Chat chat = messageObj.getChat();

            Long messageId = messageObj.getId();

            String message = messageObj.getText().trim();
            switch(message) {
                case "/start":
                case "/help":
                    sendMessage(chat.getId().toString(), helpMessage());
                    break;

                case "/simple":
                case "/medium":
                case "/hard":
                    newQuiz(chat.getId(), message.substring(1));
                    break;

                default:
                    sendMessage(chat.getId().toString(), wrongCommandMessage());
            }
        }

        //answer to callback query with user's answer
        if(update.getCallbackQuery() != null) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            processAnswer(callbackQuery.getMessage(), callbackQuery.getData());
        }
    }

    /**
     * Start new quiz
     * @param chatId Id of chat
     * @param levelName Name of difficulty level
     */
    protected void newQuiz(Long chatId, String levelName) {
        Level level;
        switch (levelName) {
            case "simple":
                level = Level.Simple;
                break;
            case "medium":
                level = Level.Medium;
                break;
            case "hard":
                level = Level.Hard;
                break;
            default:
                logger.warn("Unknown level: {}", levelName);
                level = Level.Simple;
        }

        //create quiz
        Quiz quiz = new Quiz(level);
        long quizId = quizIdSequence.incrementAndGet();
        quizCollection.put(quizId, quiz);

        //build keyboard
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboard.add(keyboardRow);
        for(QuizVariant variant : quiz.getQuizVariants()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(variant.getName());
            button.setCallbackData(String.valueOf(quizId) + ":" + variant.getName());
            keyboardRow.add(button);
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setInlineKeyboard(keyboard);

        //send message
        SendMessageParams messageParams = new SendMessageParams();
        messageParams.setChatId(chatId.toString());
        messageParams.setText(buildMessageText(quiz, false));
        messageParams.setReplyMarkup(keyboardMarkup);
        sendMessage(messageParams);
    }

    /**
     * Process answer from user
     * @param originalMessage Message with quiz
     * @param answer User answer data
     */
    protected void processAnswer(Message originalMessage, String answer) {
        if(originalMessage == null) {
            logger.warn("Empty original message");
            return;
        }

        if(answer == null) {
            logger.warn("Empty answer");
            return;
        }

        Long messageId = originalMessage.getId();
        Long chatId = originalMessage.getChat().getId();

        String[] parts = answer.split(":");

        if(parts.length != 2) {
            logger.warn("Malformed answer: {}", answer);
            return;
        }

        Long quizId;
        String variant;
        try {
            quizId = Long.decode(parts[0]);
            variant = parts[1];
        } catch(Exception e) {
            logger.warn("Cannot parse answer: " + answer, e);
            return;
        }

        Quiz quiz = quizCollection.get(quizId);
        if(quiz == null) {
            logger.warn("Quiz not found: {}", quizId);
            return;
        }

        //check answer
        String quizText = buildMessageText(quiz, true) +
                "\n" +
                (quiz.isCorrect(variant) ? "Right!" : "No. Try again?");

        EditMessageTextParams editParams = new EditMessageTextParams();
        editParams.setChatId(chatId.toString());
        editParams.setMessageId(messageId);
        editParams.setText(quizText);
        editParams.setReplyMarkup(new InlineKeyboardMarkup());
        editMessageText(editParams);
    }

    /**
     * Build message text for quiz
     * @param quiz Quiz
     * @return Text
     */
    protected String buildMessageText(Quiz quiz, boolean markCorrect) {
        StringBuilder message = new StringBuilder();
        message.append(quiz.getQuizString());
        message.append("\n");

        for(QuizVariant variant : quiz.getQuizVariants()) {
            message.append(variant.toString());

            if(markCorrect && variant.getValue() == quiz.getAnswer()) {
                message.append(" <-");
            }

            message.append("\n");
        }

        return message.toString();
    }

    /**
     * Bot help message
     * @return Message text
     */
    public String helpMessage() {
        return "This is math quiz bot. Test your math skills!\n" +
                "Type commands and solve random math problems:\n" +
                "/simple - two small numbers (10 - 100)\n" +
                "/medium - three medium numbers (100 - 1000)\n" +
                "/hard - three big numbers (1000 - 10000)";
    }

    /**
     * Bot wrong command message
     * @return Message text
     */
    public String wrongCommandMessage() {
        return "Type quiz command or /help for list of commands.";
    }
}
