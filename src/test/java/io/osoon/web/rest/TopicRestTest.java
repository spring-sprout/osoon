package io.osoon.web.rest;

import io.osoon.exception.ApiError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-30
 */
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicRestTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void existCreateTopic() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("name", "java");
		ResponseEntity<ApiError> responseEntity = this.restTemplate.postForEntity("/api/topic/create", params, ApiError.class);
		assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test
	public void createTopic() {
		String topic = "topic" + UUID.randomUUID();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("name", topic);
		ResponseEntity<ApiError> responseEntity = this.restTemplate.postForEntity("/api/topic/create", params, ApiError.class);
		assertThat(responseEntity.getBody().getStatus()).isEqualTo(topic);
	}
}
