package ru.doublebyte.mathquizbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.doublebyte.mathquizbot.bot.types.Message;
import ru.doublebyte.mathquizbot.bot.types.Update;
import ru.doublebyte.mathquizbot.bot.types.mediaentity.Chat;
import ru.doublebyte.mathquizbot.bot.types.util.ChatId;
import ru.doublebyte.mathquizbot.quiz.Quiz;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Math quiz bot
 */
public class QuizBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(QuizBot.class);

    private Map<Long, Quiz> quiz = new ConcurrentHashMap<>();


    public QuizBot(String apiUrl, String token) {
        super(apiUrl, token);
    }


    @Override
    protected void processUpdate(Update update) {
        if(update.getMessage() != null) {
            Message messageObj = update.getMessage();
            Chat chat = messageObj.getChat();

            String message = messageObj.getText().trim();
            switch(message) {
                case "/start":
                case "/help":
                    sendMessage(new ChatId(chat.getId()), helpMessage());
                    break;

                case "/simple":
                case "/medium":
                case "/hard":
                    logger.info("New problem: {}", message);
                    sendMessage(new ChatId(chat.getId()), "Your problem will be here.");
                    break;

                default:
                    sendMessage(new ChatId(chat.getId()), "Type quiz command or /help for list of commands.");
            }
        }
    }

    public String helpMessage() {
        return "This is math quiz bot. Test your math skills!\n" +
                "Type commands and solve random math problems:\n" +
                "/simple - two small numbers (10 - 100)\n" +
                "/medium - three medium numbers (100 - 1000)\n" +
                "/hard - three big numbers (1000 - 10000)";
    }
}
