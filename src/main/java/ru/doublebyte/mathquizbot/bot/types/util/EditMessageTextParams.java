package ru.doublebyte.mathquizbot.bot.types.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.doublebyte.mathquizbot.bot.types.InlineKeyboardMarkup;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageTextParams {

    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("message_id")
    private Long messageId;

    @JsonProperty("inline_message_id")
    private String inlineMessageId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("parse_mode")
    private String parseMode;

    @JsonProperty("disable_web_page_preview")
    private Boolean disableWebPagePreview;

    @JsonProperty("reply_markup")
    InlineKeyboardMarkup replyMarkup;

    ///////////////////////////////////////////////////////////////////////////

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public void setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }
}
