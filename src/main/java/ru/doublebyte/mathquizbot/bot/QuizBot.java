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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Math quiz bot
 */
public class QuizBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(QuizBot.class);

    private Map<UUID, Quiz> quizCollection = new ConcurrentHashMap<>();

    public QuizBot(String apiUrl, String token) {
        super(apiUrl, token);
    }

    /**
     * Process bot update
     * @param update Update object
     */
    @Override
    protected void processUpdate(Update update) {
        //text message from user with command
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
        UUID quizId = UUID.randomUUID();
        quizCollection.put(quizId, quiz);

        //build keyboard
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = quiz.getQuizVariants()
                .stream()
                .map(variant -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(variant.getName());
                    button.setCallbackData(quizId.toString() + ":" + variant.getName());
                    return button;
                })
                .collect(Collectors.toList());
        keyboard.add(keyboardRow);

        //send message
        SendMessageParams messageParams = new SendMessageParams();
        messageParams.setChatId(chatId.toString());
        messageParams.setText(buildMessageText(quiz, false));
        messageParams.setReplyMarkup(new InlineKeyboardMarkup(keyboard));
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
            logger.warn("Malformed answer: {}, chatId: {}, messageId: {}",
                    answer, chatId, messageId);
            return;
        }

        UUID quizId;
        String variant;
        try {
            quizId = UUID.fromString(parts[0]);
            variant = parts[1];
        } catch(Exception e) {
            logger.warn("Cannot parse answer ({}): {}, chatId: {}, messageId: {}",
                    e.getMessage(), answer, chatId, messageId);
            return;
        }

        EditMessageTextParams editParams = new EditMessageTextParams();
        editParams.setChatId(chatId.toString());
        editParams.setMessageId(messageId);
        editParams.setReplyMarkup(InlineKeyboardMarkup.emptyKeyboardMarkup());

        Quiz quiz = quizCollection.get(quizId);
        if(quiz != null) {
            editParams.setText(buildMessageText(quiz, true) +
                    (quiz.isCorrect(variant) ? "Right!" : "No. Try again?"));
        } else {
            editParams.setText(quizNotFoundMessage());
            logger.warn("Quiz not found: {}", quizId);
        }

        editMessageText(editParams);
    }

    /**
     * Build message text for quiz
     * Mark correct variant when markCorrect flag os set
     *
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

    /**
     * Message when user answers on a quiz that not found
     * @return Message text
     */
    public String quizNotFoundMessage() {
        return "This problem is no longer available :(";
    }
}
