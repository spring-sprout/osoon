package io.osoon.web.api;

import io.osoon.web.dto.TopicDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author whiteship
 */
public class TopicControllerTest extends ControllerTest {

    @Test
    public void create() throws Exception {
        // Given
        String topic = "spring";
        TopicDto topicDto = TopicDto.of(topic);

        MockHttpServletRequestBuilder request = post("/api/topic/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(topicDto));

        // When & Then
        mvc.perform(request)
            .andDo(print())
            .andDo(document("create-topic"))
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.name", Matchers.is(topic)))
        ;
    }

}
