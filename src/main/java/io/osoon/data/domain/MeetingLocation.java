package io.osoon.data.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-24
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class MeetingLocation {
	@GraphId Long id;

	/** 모임 주소 **/
	String addr;
	/** 모임 별칭 **/
	String name;
	double latitude;
	double longitude;

}
