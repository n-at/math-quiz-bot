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
            Message message = update.getMessage();
            Chat chat = message.getChat();
            sendMessage(new ChatId(chat.getId()), "Your message: " + message.getText());
        }
    }
}
