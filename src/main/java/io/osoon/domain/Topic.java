package io.osoon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-11
 */
@Entity
@Setter @Getter @ToString
@NoArgsConstructor
public class Topic {

	@Id
	@GeneratedValue
	Long id;

    /**
     * 소문자로만 지원
     */
	@Column(unique = true)
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
