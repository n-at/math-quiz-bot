package ru.doublebyte.mathquizbot.bot.types.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Telegram API response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Response {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("description")
    private String description;


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
