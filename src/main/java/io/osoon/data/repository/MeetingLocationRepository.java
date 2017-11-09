package io.osoon.data.repository;

import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author whiteship
 */
public interface MeetingLocationRepository extends Neo4jRepository<MeetingLocation, Long> {

    @Query("MATCH (ml:MeetingLocation)-[r:CREATED_BY]-(u:User) WHERE id(u) = {userId} RETURN ml")
    List<MeetingLocation> findByUser(@Param("userId") User user);

    List<MeetingLocation> findByUserEmail(String email);
}
