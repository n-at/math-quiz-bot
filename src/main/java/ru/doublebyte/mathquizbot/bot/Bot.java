package ru.doublebyte.mathquizbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.doublebyte.mathquizbot.bot.types.Message;
import ru.doublebyte.mathquizbot.bot.types.User;
import ru.doublebyte.mathquizbot.bot.types.response.GetMeResponse;
import ru.doublebyte.mathquizbot.bot.types.response.GetUpdatesResponse;
import ru.doublebyte.mathquizbot.bot.types.Update;
import ru.doublebyte.mathquizbot.bot.types.response.SendMessageResponse;
import ru.doublebyte.mathquizbot.bot.types.util.ChatId;

import java.util.*;

public abstract class Bot {

    private static Logger logger = LoggerFactory.getLogger(Bot.class);

    private String token;
    private String apiUrl;

    private RestTemplate restTemplate = new RestTemplate();

    private long lastId = 0;
    private int timeout = 0;
    private int updateLimit = 100;

    public Bot(String apiUrl, String token) {
        this.apiUrl = apiUrl;
        this.token = token;
    }

    protected abstract void processUpdate(Update update);

    /**
     * Fetch and process updates from Telegram bot API server
     */
    public void processUpdates() {
        List<Update> updates = getUpdates(lastId, updateLimit, timeout);

        lastId = updates.stream()
                .map(Update::getId)
                .max(Long::compareTo)
                .orElse(lastId-1) + 1;

        updates.parallelStream().forEach(this::processUpdate);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Construct URL for method by name
     * @param method Method name
     * @return Url with bot token and method name
     */
    private String methodUrl(String method) {
        return apiUrl + token + "/" + method;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get incoming updates
     * https://core.telegram.org/bots/api#getupdates
     *
     * @param offset id of first update
     * @param limit Number of updates returned
     * @param timeout Timeout for long polling
     * @return Updates
     */
    protected List<Update> getUpdates(long offset, int limit, int timeout) {
        try {

            UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(methodUrl("getUpdates"))
                    .queryParam("offset", offset)
                    .queryParam("limit", limit)
                    .queryParam("timeout", timeout);

            GetUpdatesResponse response = restTemplate.getForObject(
                    uri.build().encode().toUri(), GetUpdatesResponse.class);

            if(response == null) {
                logger.warn("getUpdates null result");
                return new ArrayList<>();
            }

            if(!response.isOk()) {
                logger.warn("getUpdates error: {} - {}", response.getErrorCode(), response.getDescription());
                return new ArrayList<>();
            }

            if(response.getResult() == null) {
                return new ArrayList<>();
            }

            return response.getResult();
        } catch(Exception e) {
            logger.error("getUpdates failed", e);
            return new ArrayList<>();
        }
    }

    /**
     * Get bot user info
     * https://core.telegram.org/bots/api#getme
     *
     * @return Bot info as User entity
     */
    public User getMe() {
        try {
            GetMeResponse response = restTemplate.getForObject(methodUrl("getMe"), GetMeResponse.class);

            if(response == null) {
                logger.warn("getMe null response");
                return null;
            }

            if(!response.isOk()) {
                logger.warn("getMe error: {} - {}", response.getErrorCode(), response.getDescription());
                return null;
            }

            return response.getResult();
        } catch(Exception e) {
            logger.error("getMe failed", e);
            return null;
        }
    }

    /**
     * Send simple text message
     * https://core.telegram.org/bots/api#sendmessage
     *
     * @param chatId Target chat id
     * @param text Message
     * @return Sent text
     */
    public Message sendMessage(ChatId chatId, String text) {
        try {
            if(chatId == null || (chatId.getId() == null && chatId.getUsername() == null)) {
                throw new Exception("chat_id is null");
            }

            UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(methodUrl("sendMessage"))
                    .queryParam("chat_id", chatId.getId() != null ? chatId.getId() : chatId.getUsername())
                    .queryParam("text", text);

            SendMessageResponse response = restTemplate.getForObject(
                    uri.build().encode().toUri(), SendMessageResponse.class);

            if(response == null) {
                logger.warn("sendMessage null response");
                return null;
            }

            if(!response.isOk()) {
                logger.warn("sendMessage error: {} - {}", response.getErrorCode(), response.getDescription());
                return null;
            }

            return response.getResult();
        } catch(Exception e) {
            logger.error("sendMessage failed", e);
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getToken() {
        return token;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getUpdateLimit() {
        return updateLimit;
    }

    public void setUpdateLimit(int updateLimit) {
        this.updateLimit = updateLimit;
    }
}
