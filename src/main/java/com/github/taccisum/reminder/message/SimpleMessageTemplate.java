package com.github.taccisum.reminder.message;

import com.github.taccisum.reminder.api.MessageTemplate;

/**
 * @author tac
 * @since 25/10/2018
 */
public class SimpleMessageTemplate implements MessageTemplate {
    private String code;
    private String topic;
    private String body;

    public SimpleMessageTemplate(String code, String topic, String body) {
        this.code = code;
        this.topic = topic;
        this.body = body;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleMessageTemplate{");
        sb.append("code='").append(code).append('\'');
        sb.append(", topic='").append(topic).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
