package io.osoon.data.repository;

import io.osoon.data.domain.UserFile;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

/**
 * @author whiteship
 */
public interface UserFileRepository extends Neo4jRepository<UserFile, Long> {

    Optional<UserFile> findByPath(String path, @Depth int depth);

}
