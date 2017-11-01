package io.osoon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import io.osoon.data.domain.Topic;
import io.osoon.data.domain.queryresult.TopicView;
import io.osoon.data.repository.TopicRepository;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-27
 */
@Service
public class TopicService {
	@Autowired TopicRepository repository;

	public Topic create(String name) {
		if (repository.findByName(name).isPresent()) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, name + " 이미 등록된 태그 입니다.");
		}

		return repository.save(new Topic(name));
	}

	/**
	 * 대소문자 구분 없이 소문자로만 저장 및 사용
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Page<TopicView> listByStartingName(String name, Pageable pageable) {
		return repository.findByNameStartingWith(name.toLowerCase(), pageable);
	}

	/**
	 * 토픽 수가 많지 않을 경우 전부 가져와서 클라이언트에서 분류
	 * @return
	 */
	public Iterable<Topic> listAll() {
		return repository.findAll();
	}

	public Optional<Topic> findByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Topic> findById(Long id) {
		return repository.findById(id);
	}
}
