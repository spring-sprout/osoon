package com.moilago.server.sample.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-09-18
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Meeting {
	@GraphId Long id;
	String title;
	String contents;

	public Meeting(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
