package io.osoon.repository;

import io.osoon.domain.MeetingLocation;
import io.osoon.domain.User;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
public class MeetingLocationRepositoryTest extends RepositoryTest{

    @Test
    public void findByUser() {
        User keesun = userRepository.save(User.of("keesun@email.com", "keesun"));
        MeetingLocation toz = meetingLocationRepository.save(MeetingLocation.of("Toz", keesun));

        List<MeetingLocation> byUser = meetingLocationRepository.findByUser(keesun);
        assertThat(byUser.size()).isEqualTo(1);
        assertThat(byUser.get(0).getName()).isEqualToIgnoringCase(toz.getName());
    }

}