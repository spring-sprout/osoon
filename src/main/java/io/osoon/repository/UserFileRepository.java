package io.osoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.osoon.domain.UserFile;

/**
 * @author whiteship
 */
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

}
