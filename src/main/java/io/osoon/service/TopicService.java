package io.osoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.osoon.data.domain.Topic;
import io.osoon.data.repository.TopicRepository;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-27
 */
@Service
public class TopicService {
	@Autowired TopicRepository repository;

	public Topic create(String name) {
		return repository.save(new Topic(name));
	}

	/**
	 * @TODO 사용 카운트 까지 포함되서 view 객체로 리턴 할 것.
	 * 대소문자 구분이 안됨(IgnoreCase 메서드에 붙여도 안됨)
	 * 쿼리로 짜던지 아니면 대문자로만 저장하는 값 하나 만들어서 거기서 찾을 것.
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Page<Topic> listByStartingName(String name, Pageable pageable) {
		return repository.findByNameStartingWith(name, pageable);
	}
}
