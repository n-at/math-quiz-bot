package ru.doublebyte.mathquizbot.bot.types.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.doublebyte.mathquizbot.bot.types.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMeResponse extends Response {

    @JsonProperty("result")
    private User result;


    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }
}
