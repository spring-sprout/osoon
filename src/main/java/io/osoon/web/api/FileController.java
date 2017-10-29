package io.osoon.web.api;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.domain.UserFile;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.exception.MeetingNotFoundException;
import io.osoon.exception.UserNotFoundException;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.UserFileService;
import io.osoon.service.UserService;
import io.osoon.web.dto.UserFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author whiteship
 */
@RestController
@RequestMapping("/api/meeting/{id}")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostAuthorize("/file")
    public ResponseEntity<UserFileDto> uploadFile(
            @AuthenticationPrincipal OSoonUserDetails userDetails,
            @PathVariable("id") Long meetingId,
            @RequestParam("file") MultipartFile file) {
        long userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException(meetingId));

        UserFile newFile = userFileService.store(meeting, user, file);
        return ResponseEntity.ok(new UserFileDto(newFile));
    }

}
