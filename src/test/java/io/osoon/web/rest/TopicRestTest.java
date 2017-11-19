package io.osoon.web.rest;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.osoon.data.domain.Topic;
import io.osoon.data.repository.TopicRepository;
import io.osoon.exception.ApiError;
import io.osoon.helper.DatabaseTestHelper;
import io.osoon.web.dto.TopicDto;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-30
 */
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicRestTest {

	@Autowired private TestRestTemplate restTemplate;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private DatabaseTestHelper databaseTestHelper;
	@Autowired private TopicRepository topicRepository;

	@Before
    public void before() {
	    databaseTestHelper.removeAllData();
    }

    @After
    public void after() {
	    databaseTestHelper.removeAllData();
    }

	@Test
	public void existCreateTopic() throws JsonProcessingException {
	    // Given
        Topic topic = Topic.of("java");
        topicRepository.save(topic);
        assertThat(topicRepository.findByName(topic.getName()).isPresent()).isTrue();

        TopicDto topicDto = new TopicDto();
        topicDto.setName("java");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(topicDto), headers);

        // When
		ResponseEntity<ApiError> responseEntity = this.restTemplate.postForEntity("/api/topic/create", entity, ApiError.class);

		// Then
		assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test
	public void createTopic() throws JsonProcessingException {
	    // Given
        String tagName = UUID.randomUUID().toString();
        HttpEntity<String> entity = createEntity(tagName);

		// When
		ResponseEntity<Topic> responseEntity = this.restTemplate.postForEntity("/api/topic/create", entity, Topic.class);

		// Then
		assertThat(responseEntity.getBody().getName()).isEqualTo(tagName);
	}

	private HttpEntity<String> createEntity(String name) throws JsonProcessingException {
        TopicDto topicDto = new TopicDto();
        topicDto.setName(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(objectMapper.writeValueAsString(topicDto), headers);
    }
}
