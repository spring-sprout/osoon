package io.osoon.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.osoon.data.domain.Comment;
import io.osoon.data.repository.CommentRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.service.CommentService;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@RestController
@RequestMapping("/samples/comment")
public class CommentController {
	private Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired private CommentRepository repository;
	@Autowired private CommentService service;
	@Autowired private UserRepository userRepository;
	@Autowired private MeetingRepository meetingRepository;

	@PutMapping("/write")
	public Comment write(long meetingId, long userId, String contents) {
		return service.write(userRepository.findById(userId).get(), meetingRepository.findById(meetingId).get(), contents);
	}

	@GetMapping("/show")
	public Comment show(long id) {
		return repository.findById(id).get();
	}


	@GetMapping("list")
	public Page<Comment> list(long meetingId) {
		return repository.getCommentsBelongTo(meetingId, PageRequest.of(0, 10));
	}

}
