package io.osoon.repository;

import io.osoon.domain.MeetingLocation;
import io.osoon.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author whiteship
 */
public interface MeetingLocationRepository extends Neo4jRepository<MeetingLocation, Long> {

    @Query("MATCH (ml:MeetingLocation)-[r:CREATED_BY]-(u:User) WHERE id(u) = {userId} RETURN ml")
    List<MeetingLocation> findByUser(@Param("userId") User user);

    List<MeetingLocation> findByUserEmail(String email);

    Optional<MeetingLocation> findByAddr(String addr);
}
