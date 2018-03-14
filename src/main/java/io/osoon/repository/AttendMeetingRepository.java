package io.osoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.osoon.domain.AttendMeeting;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @author 백기선 (whiteship2000@gmail.com)
 * @since 2017-10-12
 */
@Repository
public interface AttendMeetingRepository extends JpaRepository<AttendMeeting, Long> {

}
