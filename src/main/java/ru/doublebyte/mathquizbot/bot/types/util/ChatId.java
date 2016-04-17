package ru.doublebyte.mathquizbot.bot.types.util;

/**
 * Represents target chat id or username of channel
 */
public class ChatId {

    private Long id;
    private String username;

    public ChatId(Long id) {
        this.id = id;
    }

    public ChatId(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
