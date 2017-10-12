package io.osoon.data.domain;

import org.neo4j.ogm.annotation.GraphId;
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
	@GraphId Long id;
	private String name;
}
