package io.osoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.osoon.domain.Meeting;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @author 백기선 (whiteship2000@gmail.com)
 * @since 2017-09-19
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
