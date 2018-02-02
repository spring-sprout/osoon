package io.osoon.repository.queryresult;

import io.osoon.domain.Topic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-30
 */
@QueryResult
@Getter @Setter
public class TopicView {
	Topic topic;
	int count;

	@Override public String toString() {
		return "TopicView{" +
			"topic=" + topic +
			", count=" + count +
			'}';
	}
}