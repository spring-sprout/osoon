package com.moilago.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moilago.server.sample.domain.Comment;
import com.moilago.server.sample.repository.CommentRepository;
import com.moilago.server.sample.repository.MeetingRepository;
import com.moilago.server.sample.repository.UserRepository;
import com.moilago.server.sample.service.CommentService;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@RestController
@RequestMapping("/samples/comment")
public class SampleCommentController {
	private Logger logger = LoggerFactory.getLogger(SampleCommentController.class);

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
