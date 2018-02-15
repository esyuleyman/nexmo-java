package com.nexmo.client.stitch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.nexmo.client.NexmoUnexpectedException;
import com.nexmo.client.legacyutils.PageLinks;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Ergyun Syuleyman on 2/15/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InAppConversationMembersPage implements Iterable<InAppConversationMember> {
    private String conversationId;

    private int count;
    private int pageSize;
    private int recordIndex;

    private PageLinks links;
    private EmbeddedInAppConversationMembers embedded;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getCount() {
        return count;
    }

    @JsonProperty("page_size")
    public int getPageSize() {
        return pageSize;
    }

    @JsonProperty("record_index")
    public int getRecordIndex() {
        return recordIndex;
    }

    @JsonProperty("_links")
    public PageLinks getLinks() {
        return links;
    }

    @JsonProperty("_embedded")
    public EmbeddedInAppConversationMembers getEmbedded() {
        return embedded;
    }

    @Override
    public Iterator<InAppConversationMember> iterator() {
        return new ArrayIterator<>(embedded.getInAppConversationMembers());
    }

    public static InAppConversationMembersPage fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(json, InAppConversationMembersPage.class);
        } catch (IOException jpe) {
            throw new NexmoUnexpectedException("Failed to produce InAppConversationMembersPage object from json.", jpe);
        }
    }
}
