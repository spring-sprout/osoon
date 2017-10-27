package io.osoon.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import io.osoon.data.domain.Topic;
import io.osoon.service.TopicService;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-27
 */
@RestController
@RequestMapping("/api/topic/")
public class TopicController {
	@Autowired TopicService service;

	@PostMapping("create")
	public Topic create(@RequestParam String name) {
		return service.create(name);
	}

	@GetMapping("listByStartingName")
	public Page<Topic> listByStartingName(@RequestParam String name, Pageable pageable) {
		return service.listByStartingName(name, pageable);
	}
}
