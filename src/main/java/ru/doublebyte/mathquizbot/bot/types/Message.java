package ru.doublebyte.mathquizbot.bot.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.doublebyte.mathquizbot.bot.types.mediaentity.*;

import java.util.List;

/**
 * https://core.telegram.org/bots/api#message
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("message_id")
    private Long id;

    @JsonProperty("from")
    private User from;

    @JsonProperty("date")
    private Integer date;

    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("forward_from")
    private User forwardFrom;

    @JsonProperty("forward_date")
    private Integer forwardDate;

    @JsonProperty("reply_to_message")
    private Message replyToMessage;

    @JsonProperty("text")
    private String text;

    @JsonProperty("entities")
    private List<MessageEntity> entities;

    @JsonProperty("audio")
    private Audio audio;

    @JsonProperty("document")
    private Document document;

    @JsonProperty("photo")
    private List<PhotoSize> photo;

    @JsonProperty("sticker")
    private Sticker sticker;

    @JsonProperty("video")
    private Video video;

    @JsonProperty("voice")
    private Voice voice;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("contact")
    private Contact contact;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("venue")
    private Venue venue;

    @JsonProperty("new_chat_member")
    private User newChatMember;

    @JsonProperty("left_chat_member")
    private User leftChatMember;

    @JsonProperty("new_chat_title")
    private String newChatTitle;

    @JsonProperty("new_chat_photo")
    private List<PhotoSize> newChatPhoto;

    @JsonProperty("delete_chat_photo")
    private Boolean deleteChatPhoto;

    @JsonProperty("group_chat_cteated")
    private Boolean groupChatCreated;

    @JsonProperty("supergroup_chat_created")
    private Boolean supergroupChatCreated;

    @JsonProperty("channel_chat_created")
    private Boolean channelChatCreated;

    @JsonProperty("migrate_to_chat_id")
    private Long migrateToChatId;

    @JsonProperty("migrate_from_chat_id")
    private Long migrateFromChatId;

    @JsonProperty("pinned_message")
    private Message pinnedMessage;

    ///////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getForwardFrom() {
        return forwardFrom;
    }

    public void setForwardFrom(User forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    public Integer getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Integer forwardDate) {
        this.forwardDate = forwardDate;
    }

    public Message getReplyToMessage() {
        return replyToMessage;
    }

    public void setReplyToMessage(Message replyToMessage) {
        this.replyToMessage = replyToMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MessageEntity> entities) {
        this.entities = entities;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getNewChatMember() {
        return newChatMember;
    }

    public void setNewChatMember(User newChatMember) {
        this.newChatMember = newChatMember;
    }

    public User getLeftChatMember() {
        return leftChatMember;
    }

    public void setLeftChatMember(User leftChatMember) {
        this.leftChatMember = leftChatMember;
    }

    public String getNewChatTitle() {
        return newChatTitle;
    }

    public void setNewChatTitle(String newChatTitle) {
        this.newChatTitle = newChatTitle;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return newChatPhoto;
    }

    public void setNewChatPhoto(List<PhotoSize> newChatPhoto) {
        this.newChatPhoto = newChatPhoto;
    }

    public Boolean getDeleteChatPhoto() {
        return deleteChatPhoto;
    }

    public void setDeleteChatPhoto(Boolean deleteChatPhoto) {
        this.deleteChatPhoto = deleteChatPhoto;
    }

    public Boolean getGroupChatCreated() {
        return groupChatCreated;
    }

    public void setGroupChatCreated(Boolean groupChatCreated) {
        this.groupChatCreated = groupChatCreated;
    }

    public Boolean getSupergroupChatCreated() {
        return supergroupChatCreated;
    }

    public void setSupergroupChatCreated(Boolean supergroupChatCreated) {
        this.supergroupChatCreated = supergroupChatCreated;
    }

    public Boolean getChannelChatCreated() {
        return channelChatCreated;
    }

    public void setChannelChatCreated(Boolean channelChatCreated) {
        this.channelChatCreated = channelChatCreated;
    }

    public Long getMigrateToChatId() {
        return migrateToChatId;
    }

    public void setMigrateToChatId(Long migrateToChatId) {
        this.migrateToChatId = migrateToChatId;
    }

    public Long getMigrateFromChatId() {
        return migrateFromChatId;
    }

    public void setMigrateFromChatId(Long migrateFromChatId) {
        this.migrateFromChatId = migrateFromChatId;
    }

    public Message getPinnedMessage() {
        return pinnedMessage;
    }

    public void setPinnedMessage(Message pinnedMessage) {
        this.pinnedMessage = pinnedMessage;
    }
}
