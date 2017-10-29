package io.osoon.data.repository;

import io.osoon.data.domain.UserFile;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author whiteship
 */
public interface UserFileRepository extends Neo4jRepository<UserFile, Long> {
}
