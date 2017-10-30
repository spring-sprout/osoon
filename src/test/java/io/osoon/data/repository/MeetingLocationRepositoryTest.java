package io.osoon.data.repository;

import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingLocationRepositoryTest {

    @Autowired MeetingLocationRepository meetingLocationRepository;
    @Autowired UserRepository userRepository;

    @Before
    public void before() {
        userRepository.deleteAll();;
        meetingLocationRepository.deleteAll();
    }

    @Test
    public void findByUser() {
        User keesun = userRepository.save(User.of("keesun@email.com", "keesun"));
        MeetingLocation toz = meetingLocationRepository.save(MeetingLocation.of("Toz", keesun));

        List<MeetingLocation> byUser = meetingLocationRepository.findByUser(keesun);
        assertThat(byUser.size()).isEqualTo(1);
        assertThat(byUser.get(0).getName()).isEqualToIgnoringCase(toz.getName());
    }

}