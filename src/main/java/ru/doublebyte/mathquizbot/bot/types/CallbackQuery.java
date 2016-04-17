package ru.doublebyte.mathquizbot.bot.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://core.telegram.org/bots/api#callbackquery
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackQuery {

    @JsonProperty("id")
    private String id;

    @JsonProperty("user")
    private User from;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("inline_message_id")
    private String inlineMessageId;

    @JsonProperty("data")
    private String data;

    ///////////////////////////////////////////////////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public void setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
