package io.osoon.web.api;

import io.osoon.data.domain.Topic;
import io.osoon.data.domain.queryresult.TopicView;
import io.osoon.service.TopicService;
import io.osoon.web.dto.TopicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-27
 */
@RestController
@RequestMapping("/api/topic/")
public class TopicController {
	@Autowired TopicService service;

	@PostMapping("create")
	public Topic create(@RequestBody TopicDto topicDto) {
		return service.create(topicDto.getName());
	}

	@GetMapping("listByStartingName")
	public Page<TopicView> listByStartingName(@RequestParam String name, Pageable pageable) {
		return service.listByStartingName(name, pageable);
	}

	@GetMapping("listAll")
	public Iterable<Topic> listAll() {
		return service.listAll();
	}
}
