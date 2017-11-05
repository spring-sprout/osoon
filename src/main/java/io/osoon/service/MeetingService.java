package io.osoon.service;

import io.osoon.data.domain.*;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingLocationRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-21
 */
@Service
public class MeetingService {
	private Logger logger = LoggerFactory.getLogger(MeetingService.class);

	@Autowired private MeetingRepository repository;
	@Autowired private AttendMeetingRepository attendMeetingRepository;
	@Autowired private UserService userService;
	@Autowired private TopicService topicService;
	@Autowired private UserFileService userFileService;
    @Autowired private UserFileRepository userFileRepository;
    @Autowired private MeetingLocationRepository meetingLocationRepository;

	public Meeting create(User user, Meeting meeting) {
		Meeting newMeeting = Meeting.of(meeting.getTitle(), meeting.getContents());
		newMeeting.setMeetingStatus(meeting.getMeetingStatus());
		newMeeting.setCoverImage(meeting.getCoverImage());
		newMeeting.setMaxAttendees(meeting.getMaxAttendees());
		newMeeting.setAutoConfirm(meeting.isAutoConfirm());
        newMeeting.setOnlineType(meeting.getOnlineType());
        newMeeting.setMeetStartAt(meeting.getMeetStartAt());
        newMeeting.setMeetEndAt(meeting.getMeetEndAt());
        newMeeting.addAdmin(user);
        newMeeting.setTopics(initTopics(meeting.getTopics()));
        newMeeting = repository.save(newMeeting);

		user.create(newMeeting);
		userService.saveOne(user);

        MeetingLocation location = meeting.getLocation();
        location.setUser(user);
        MeetingLocation newLocation = meetingLocationRepository.save(location);

        newMeeting.setLocation(newLocation);
        newMeeting = repository.save(newMeeting);

        return newMeeting;
	}

    public void join(Meeting meeting, User user) {
		if (!Meeting.MeetingStatus.PUBLISHED.equals(meeting.getMeetingStatus())) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "참여 불가능한 모입니다.");
		}

		if (repository.isJoinMeeting(meeting.getId(), user.getId())) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "이미 참여한 모임입니다.");
		}

		user.attendTo(meeting);

		userService.saveOne(user);
	}

	public void leave(long meetingId, long userId) {
		attendMeetingRepository.delete(repository.getAttendMeetingFromUserIdAndMeetingId(userId, meetingId).orElseThrow(NullPointerException::new));
	}

	/**
	 * 모임 생성자만 모임 상태 변경 가능
	 * @param meeting
	 * @param meetingStatus
	 * @param ownerId
	 */
	public void changeStatus(Meeting meeting, Meeting.MeetingStatus meetingStatus, Long ownerId) {
		checkMeetingOwner(meeting, ownerId);

		meeting.setMeetingStatus(meetingStatus);
		repository.save(meeting);
	}

	public Optional<Meeting> findById(long meetingId) {
		return repository.findById(meetingId);
	}

	@Transactional
	public Meeting update(Meeting target, long originId, Long ownerId) {
		target.setId(originId);
		target.setTopics(initTopics(target.getTopics()));
		checkMeetingOwner(target, ownerId);

		return repository.save(target);
	}

	@Transactional
	public void save(Meeting meeting) {
		repository.save(meeting);
	}

	private List<Topic> initTopics(List<Topic> source) {
		List<Topic> topics = new ArrayList<>();
		for (Topic topic : source) {
			topicService.findById(topic.getId()).ifPresent(target -> topics.add(target));
		}
		return topics;
	}


	private void checkMeetingOwner(Meeting meeting, Long ownerId) {
        if (!repository.isOwner(meeting.getId(), ownerId)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "변경 가능한 모임이 없거나 모임 생성자가 아닙니다.");
        }
    }

	public UserFile updateImage(User user, Meeting meeting, MultipartFile file) {
		// 1. 이미지 파일 여부 확인
        String mimeType = file.getContentType();
        String type = mimeType.split("/")[0];
        if (!type.equalsIgnoreCase("image"))
        {
            throw new IllegalArgumentException(file.getOriginalFilename() + " is not image file.");
        }

        // 2. 저장하고 UserFile 받아오기
        UserFile userFile = userFileService.store(meeting, user, file);
        userFile.setFileType(UserFile.FileType.IMAGE);
        userFileRepository.save(userFile);

        // 3. 썸네일 만들기 (비동기)
        userFileService.createThumbnail(userFile);

        // 3. 기본 모임 이미지 삭제
        userFileService.delete(meeting.getCoverImage());

        // 4. 새 UserFile로 모임 이미지 교체
        meeting.setCoverImage(userFile.getPath());

		// 5. 모임 정보 수정
		meeting.update();
        repository.save(meeting);

		return userFile;
	}

}
