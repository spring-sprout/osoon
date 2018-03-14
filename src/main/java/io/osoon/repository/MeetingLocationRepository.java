package io.osoon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.osoon.domain.MeetingLocation;
import io.osoon.domain.User;

/**
 * @author whiteship
 */
public interface MeetingLocationRepository extends JpaRepository<MeetingLocation, Long> {

    Optional<MeetingLocation> findByAddr(String addr);

    List<MeetingLocation> findByUser(User user);
}
