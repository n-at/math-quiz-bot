package ru.doublebyte.mathquizbot.bot.types.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.doublebyte.mathquizbot.bot.types.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponse extends Response {

    @JsonProperty("result")
    private Message result;


    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }
}
