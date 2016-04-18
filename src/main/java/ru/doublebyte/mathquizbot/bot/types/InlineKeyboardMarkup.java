package ru.doublebyte.mathquizbot.bot.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * https://core.telegram.org/bots/api#inlinekeyboardmarkup
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineKeyboardMarkup {

    @JsonProperty("inline_keyboard")
    private List<List<InlineKeyboardButton>> inlineKeyboard;

    ///////////////////////////////////////////////////////////////////////////

    public InlineKeyboardMarkup() {

    }

    public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> inlineKeyboard) {
        this.inlineKeyboard = inlineKeyboard;
    }

    public static InlineKeyboardMarkup emptyKeyboardMarkup() {
        return new InlineKeyboardMarkup(new ArrayList<>());
    }

    ///////////////////////////////////////////////////////////////////////////

    public List<List<InlineKeyboardButton>> getInlineKeyboard() {
        return inlineKeyboard;
    }

    public void setInlineKeyboard(List<List<InlineKeyboardButton>> inlineKeyboard) {
        this.inlineKeyboard = inlineKeyboard;
    }
}
