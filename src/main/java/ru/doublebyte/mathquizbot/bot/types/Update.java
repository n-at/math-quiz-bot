package ru.doublebyte.mathquizbot.bot.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://core.telegram.org/bots/api#update
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {

    @JsonProperty("update_id")
    private long id;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("inline_query")
    private InlineQuery inlineQuery;

    @JsonProperty("chosen_inline_result")
    private ChosenInlineResult chosenInlineResult;

    @JsonProperty("callback_query")
    private CallbackQuery callbackQuery;

    ///////////////////////////////////////////////////////////////////////////

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public InlineQuery getInlineQuery() {
        return inlineQuery;
    }

    public void setInlineQuery(InlineQuery inlineQuery) {
        this.inlineQuery = inlineQuery;
    }

    public ChosenInlineResult getChosenInlineResult() {
        return chosenInlineResult;
    }

    public void setChosenInlineResult(ChosenInlineResult chosenInlineResult) {
        this.chosenInlineResult = chosenInlineResult;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public void setCallbackQuery(CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
    }
}
