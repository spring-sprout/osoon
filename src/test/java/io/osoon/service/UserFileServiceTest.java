package io.osoon.service;

import io.osoon.config.properties.OSoonProperties;
import io.osoon.data.domain.UserFile;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserFileServiceTest {

    @Autowired UserFileService userFileService;
    @Autowired OSoonProperties oSoonProperties;

    @Before
    public void before() {
        cleanUp();
    }

    @After
    public void after() {
        cleanUp();
    }

    private void cleanUp() {
        Path path = Paths.get(oSoonProperties.getUploadFileRootPath());
        if (path.toFile().exists()) {
            boolean result = FileUtils.deleteQuietly(path.toFile());
            assertThat(result).isTrue();
        }
    }

    @Test
    public void createThumbnail() throws IOException {
        // Given
        UserFile userFile = new UserFile();
        userFile.setPath("test-cover-image.jpeg");

        Path path = Paths.get(oSoonProperties.getUploadFileRootPath(), userFile.getPath());
        path.getParent().toFile().mkdirs();
        Files.copy(new ClassPathResource("/test-cover-image.jpeg").getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        assertThat(path.toFile().exists()).isTrue();

        // When
        String thumbnail = userFileService.createThumbnail(userFile);

        // Then
        assertThat(new File(thumbnail).exists()).isTrue();
    }


}