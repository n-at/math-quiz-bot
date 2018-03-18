package ru.doublebyte.mathquizbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.doublebyte.mathquizbot.bot.Bot;
import ru.doublebyte.mathquizbot.bot.QuizBot;

import javax.sql.DataSource;

@Configuration
public class BotConfiguration {

    @Value("${bot.api_url}")
    private String apiUrl;

    @Value("${bot.token}")
    private String token;

    @Bean
    public Bot getBot() {
        return new QuizBot(apiUrl, token, getQuizService());
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.bot")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public QuizService getQuizService() {
        return new QuizService(getJdbcTemplate());
    }

}
