package io.osoon.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.osoon.data.domain.AttendMeeting;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-12
 */
public interface AttendMeetingRepository extends PagingAndSortingRepository<AttendMeeting, Long> {

}
