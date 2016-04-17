package ru.doublebyte.mathquizbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.doublebyte.mathquizbot.bot.Bot;
import ru.doublebyte.mathquizbot.bot.QuizBot;

@Configuration
public class BotConfiguration {

    @Value("${bot.api_url}")
    private String apiUrl;

    @Value("${bot.token}")
    private String token;

    @Bean
    public Bot getBot() {
        return new QuizBot(apiUrl, token);
    }

}
