package io.osoon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.Topic;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingLocationRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.exception.MeetingPermissionException;
import io.osoon.exception.MeetingUpdateException;
import io.osoon.exception.OSoonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author whiteship
 */
@Service
public class MeetingUpdateService {

    private Logger logger = LoggerFactory.getLogger(MeetingService.class);

    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private MeetingLocationRepository locationRepository;
    @Autowired private LocationService locationService;
    @Autowired private TopicService topicService;
    @Autowired private MeetingRepository meetingRepository;

    @Transactional
    public Meeting update(Meeting existingMeeting, JsonNode updateBody, User user) {
        if (!existingMeeting.isAdmin(user)) {
            throw new MeetingPermissionException(user.getName(), "모임 수정", HttpStatus.BAD_REQUEST);
        }

        updateMeetingStringProperty(updateBody, "title", true, existingMeeting::setTitle);
        updateMeetingStringProperty(updateBody, "contents", false, existingMeeting::setContents);
        updateMeetingStringProperty(updateBody, "coverImage", false, existingMeeting::setCoverImage);
        updateMeetingIntProperty(updateBody, "maxAttendees", existingMeeting::setMaxAttendees);
        updateMeetingBooleanProperty(updateBody, "isAutoConfirm", existingMeeting::setAutoConfirm);
        updateMeetingDateProperty(updateBody, "meetStartAt", existingMeeting::setMeetStartAt);
        updateMeetingDateProperty(updateBody, "meetEndAt", existingMeeting::setMeetStartAt);
        updateMeetingDateProperty(updateBody, "createdAt", existingMeeting::setMeetStartAt);
        updateMeetingDateProperty(updateBody, "updatedAt", existingMeeting::setMeetStartAt);
        updateMeetingDateProperty(updateBody, "registOpenAt", existingMeeting::setMeetStartAt);
        updateMeetingDateProperty(updateBody, "registCloseAt", existingMeeting::setMeetStartAt);
        updateMeetingOnlineType(updateBody, existingMeeting);
        updateMeetingOnOffType(updateBody, existingMeeting);
        updateMeetingStatus(updateBody, existingMeeting);
        updateMeetingLocation(updateBody, existingMeeting, user);
        updateAdmins(updateBody, existingMeeting);
        updateTopics(updateBody, existingMeeting);

        return meetingRepository.save(existingMeeting);
    }

    private void updateTopics(JsonNode updateBody, Meeting existingMeeting) {
        if (updateBody.has("topics")) {
            ArrayNode topicsNode = (ArrayNode) updateBody.get("topics");
            Set<Topic> topics = new HashSet<>();
            topicsNode.forEach(node -> {
                try {
                    Topic topic = objectMapper.treeToValue(node, Topic.class);
                    Topic loadedTopic = topicService.loadOrCreateNewTopic(topic);
                    topics.add(loadedTopic);
                } catch (JsonProcessingException e) {
                    throw new MeetingUpdateException("모임 주제", e.getCause());
                }
            });
            existingMeeting.setTopics(topics);
        }
    }

    private void updateMeetingStatus(JsonNode updateBody, Meeting existingMeeting) {
        if (updateBody.has("meetingStatus")) {
            String value = updateBody.get("meetingStatus").textValue();
            if (StringUtils.isEmpty(value)) {
                throw new OSoonException("모임 상태 값을 반드시 입력해야 합니다.");
            }
            Meeting.MeetingStatus meetingStatus = Meeting.MeetingStatus.valueOf(value);
            existingMeeting.setMeetingStatus(meetingStatus);
        }
    }

    private void updateAdmins(JsonNode updateBody, Meeting existingMeeting) {
        if (updateBody.has("admins")) {
            ArrayNode adminsNode = (ArrayNode) updateBody.get("admins");
            Set<User> admins = new HashSet<>();
            adminsNode.forEach(node -> {
                try {
                    User user = objectMapper.treeToValue(node, User.class);
                    Optional<User> byId = userRepository.findById(user.getId(), 1);
                    byId.ifPresent(admins::add);
                } catch (JsonProcessingException e) {
                    throw new MeetingUpdateException("모임 관리자", e.getCause());
                }
            });
            existingMeeting.setAdmins(admins);
        }
    }

    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @param updateBody
     * @param propertyName
     * @param setter
     */
    private void updateMeetingDateProperty(JsonNode updateBody, String propertyName, Consumer<Date> setter) {
        if (updateBody.has(propertyName)) {
            String dateString = updateBody.get(propertyName).textValue();
            if (StringUtils.isEmpty(dateString)) {
                throw new OSoonException("모임 " + propertyName + "은 반드시 입력해야 합니다.");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = Date.from(ZonedDateTime.parse(dateString, formatter).toInstant());
            setter.accept(date);
        }
    }

    private void updateMeetingLocation(JsonNode updateBody, Meeting existingMeeting, User user) {
        if (updateBody.has("location")) {
            JsonNode jsonNode = updateBody.get("location");
            try {
                MeetingLocation meetingLocation = objectMapper.treeToValue(jsonNode, MeetingLocation.class);
                MeetingLocation loadedLocation = locationService.loadOrCreateNewLocation(meetingLocation);
                loadedLocation.setUser(user);
                existingMeeting.setLocation(loadedLocation);
            } catch (JsonProcessingException e) {
                throw new MeetingUpdateException("모임 장소", e.getCause());
            }
        }
    }

    private void updateMeetingStringProperty(JsonNode updateBody,
                                             String propertyName,
                                             boolean isRequired,
                                             Consumer<String> setter) {
        if (updateBody.has(propertyName)) {
            String value = updateBody.get(propertyName).asText();
            if (isRequired && StringUtils.isEmpty(value)) {
                throw new OSoonException("모임 " + propertyName + "은 반드시 입력해야 합니다.");
            }
            setter.accept(value);
        }
    }

    private void updateMeetingIntProperty(JsonNode updateBody, String propertyName, Consumer<Integer> setter) {
        if (updateBody.has(propertyName)) {
            int value = updateBody.get(propertyName).asInt();
            setter.accept(value);
        }
    }

    private void updateMeetingBooleanProperty(JsonNode updateBody, String propertyName, Consumer<Boolean> setter) {
        if (updateBody.has(propertyName)) {
            boolean value = updateBody.get(propertyName).asBoolean();
            setter.accept(value);
        }
    }

    private void updateMeetingOnOffType(JsonNode updateBody,
                                        Meeting existingMeeting) {
        if (updateBody.has("meetingOnOffType")) {
            String value = updateBody.get("meetingOnOffType").asText();
            if (StringUtils.isEmpty(value)) {
                throw new OSoonException("모임 온/오프 타입은 반드시 입력해야 합니다.");
            }
            Meeting.MeetingOnOffType onOffType = Meeting.MeetingOnOffType.valueOf(value);
            existingMeeting.setMeetingOnOffType(onOffType);
        }
    }

    private void updateMeetingOnlineType(JsonNode updateBody,
                                         Meeting existingMeeting) {
        if (updateBody.has("onlineType")) {
            String value = updateBody.get("onlineType").asText();
            if (StringUtils.isEmpty(value)) {
                throw new OSoonException("모임 온라인 타입은 반드시 입력해야 합니다.");
            }
            Meeting.OnlineType onlineType = Meeting.OnlineType.valueOf(value);
            existingMeeting.setOnlineType(onlineType);
        }
    }
}
