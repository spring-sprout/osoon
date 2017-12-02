package io.osoon.service;

import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.repository.MeetingLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author whiteship
 */
@Service
public class LocationService {

    @Autowired private MeetingLocationRepository locationRepository;

    public MeetingLocation loadOrCreateNewLocation(MeetingLocation location) {
        Optional<MeetingLocation> byAddr = locationRepository.findByAddr(location.getAddr());
        return byAddr.orElseGet(() -> locationRepository.save(location));
    }

}
