package ru.doublebyte.mathquizbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bot {

    private static Logger logger = LoggerFactory.getLogger(Bot.class);

    private String token;
    private String apiUrl;

    public Bot(String apiUrl, String token) {
        this.apiUrl = apiUrl;
        this.token = token;
    }

    /**
     * Fetch and process updates from Telegram bot API server
     */
    public void processUpdates() {
        logger.info("Fetching updates...");
    }

}
