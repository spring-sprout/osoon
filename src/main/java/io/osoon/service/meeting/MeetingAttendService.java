package io.osoon.service.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingLocationRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserFileRepository;
import io.osoon.service.TopicService;
import io.osoon.service.UserFileService;
import io.osoon.service.UserService;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-09
 */
@Service
public class MeetingAttendService {
	@Autowired private MeetingRepository repository;
	@Autowired private AttendMeetingRepository attendMeetingRepository;
	@Autowired private UserService userService;

	@Transactional
	public void attend(Meeting meeting, User user) {
		if (!Meeting.MeetingStatus.PUBLISHED.equals(meeting.getMeetingStatus())) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "참여 불가능한 모입니다.");
		}

		if (meeting.isAttendBy(user)) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "이미 참여한 모임입니다.");
		}

		if (meeting.getMaxAttendees() <= 0 || attendMeetingRepository.countByMeetingId(meeting.getId()) >= meeting.getMaxAttendees()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "참여 할 수 없습니다. 참여 인원을 확인하세요.");
		}

		meeting.attendBy(user);
		repository.save(meeting);
	}

	@Transactional
	public void attendCancel(long meetingId, long userId) {
		Meeting meeting = repository.findById(meetingId).get();

		User user = userService.findById(userId).get();
		if (meeting.isAttendBy(user)) {
			meeting.attendCancel(user);
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "취소할 수 없ㅅ브니다.");
		}
		repository.save(meeting);
	}
}