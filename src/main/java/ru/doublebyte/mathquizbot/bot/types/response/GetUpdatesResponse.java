package ru.doublebyte.mathquizbot.bot.types.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.doublebyte.mathquizbot.bot.types.Update;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUpdatesResponse extends Response {

    @JsonProperty("result")
    private List<Update> result;


    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}
