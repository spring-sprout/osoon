package io.osoon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-11
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Topic {

	@Id @GeneratedValue Long id;

    /**
     * 소문자로만 지원
     */
	@Index(unique = true)
	private String name;

	public Topic(String name) {
		this.name = name.toLowerCase();
	}

	public static Topic of(String name) {
		Topic topic = new Topic();
		topic.name = name;
		return topic;
	}
}
