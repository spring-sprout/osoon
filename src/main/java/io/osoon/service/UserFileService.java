package io.osoon.service;

import io.osoon.config.properties.OSoonProperties;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.domain.UserFile;
import io.osoon.data.repository.UserFileRepository;
import io.osoon.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author whiteship
 */
@Service
public class UserFileService {

    private Logger logger = LoggerFactory.getLogger(UserFileService.class);

    @Autowired
    private OSoonProperties properties;

    @Autowired
    private UserFileRepository userFileRepository;

    public UserFile store(Meeting meeting, User user, MultipartFile file) {
        Path uploadFileRootPath = Paths.get(properties.getUploadFileRootPath());
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new StorageException("Name of the file should not be null");
        }

        String filename = StringUtils.cleanPath(originalFilename);

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) { // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }

            UserFile userFile = UserFile.of(file, meeting, user);

            Files.copy(file.getInputStream(), uploadFileRootPath.resolve(userFile.getPath()),
                    StandardCopyOption.REPLACE_EXISTING);

            // 썸네일 생성

            return userFileRepository.save(userFile);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    public void delete(String imagePath) {
        try {
            Files.deleteIfExists(Paths.get(properties.getUploadFileRootPath(), imagePath));
        } catch (IOException e) {
            logger.error("Failed to delete '" + imagePath + "'");
        }
    }
}
