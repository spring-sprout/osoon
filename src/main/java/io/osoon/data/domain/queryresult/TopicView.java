package io.osoon.data.domain.queryresult;

import org.springframework.data.neo4j.annotation.QueryResult;

import io.osoon.data.domain.Topic;
import lombok.Getter;
import lombok.Setter;

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
