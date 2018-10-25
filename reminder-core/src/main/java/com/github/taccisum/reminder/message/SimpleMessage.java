package com.github.taccisum.reminder.message;

import com.github.taccisum.reminder.api.Message;

/**
 * @author tac
 * @since 24/10/2018
 */
public class SimpleMessage implements Message {
    private String topic;
    private String body;

    @Override
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
