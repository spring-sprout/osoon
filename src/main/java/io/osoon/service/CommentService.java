package io.osoon.service;

import io.osoon.data.domain.Comment;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.CommentRepository;
import io.osoon.data.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-09-29
 */
@Service
public class CommentService {
	@Autowired private CommentRepository repository;
	@Autowired private MeetingRepository meetingRepository;

	public Comment write(User user, Meeting meeting, String contents) {

		if (!meetingRepository.isJoinMeeting(meeting.getId(), user.getId())) {
			throw new RuntimeException("본인이 참여하지 않은 모임에는 댓글을 작성할 수 없습니다.");
		}

		return repository.save(Comment.of(user, meeting, contents));
	}
}
