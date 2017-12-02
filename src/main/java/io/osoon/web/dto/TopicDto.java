package io.osoon.web.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author whiteship
 */
@Getter @Setter
public class TopicDto {

    private String name;

    public static TopicDto of(String name) {
        TopicDto topic = new TopicDto();
        topic.setName(name);
        return topic;
    }
}
