package io.osoon.data.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-11
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Topic {

	@Id @GeneratedValue Long id;
	@Index(unique = true)
	private String name;
}
