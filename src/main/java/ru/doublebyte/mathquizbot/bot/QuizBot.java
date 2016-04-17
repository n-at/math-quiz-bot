package ru.doublebyte.mathquizbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.doublebyte.mathquizbot.bot.types.Update;

/**
 * Math quiz bot
 */
public class QuizBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(QuizBot.class);

    public QuizBot(String apiUrl, String token) {
        super(apiUrl, token);
    }

    @Override
    protected void processUpdate(Update update) {

    }
}
