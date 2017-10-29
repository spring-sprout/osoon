package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author whiteship
 */
@NodeEntity
@Setter
@Getter
@NoArgsConstructor
public class UserFile {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 파일 원본 이름
     */
    private String name;

    /**
     * 파일 상대 경로, 파일 루트 디렉토리는 설정값에서 읽어와서 조합해야 함.
     */
    private String path;

    /**
     * 파일 크기
     */
    private long size;

    /**
     * 파일 종류
     */
    private FileType fileType;

    /**
     * 파일 올린 사람
     */
    @Relationship(type = "Uploaded_By")
    private User uploader;

    /**
     * 파일 올린 모임 (이 모임 삭제하면 관련 파일도 삭제)
     */
    @Relationship(type = "Belong_To")
    private Meeting meeting;

    /**
     * 파일 올린 일시
     */
    private Date uploadedAt;

    /**
     * 파일 삭제는 상태값만 변경하고 나중에 배치로 처리 (또는 비동기로)
     */
    private boolean deleted = false;

    public static UserFile of(MultipartFile file, Meeting meeting, User user) {
        UserFile userFile = new UserFile();
        userFile.setName(file.getOriginalFilename());
        userFile.setSize(file.getSize());
        userFile.setMeeting(meeting);
        userFile.setUploader(user);
        userFile.setPath(meeting.getId() + File.separator + file.getOriginalFilename().hashCode());

        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        userFile.setUploadedAt(Date.from(utc.toInstant()));
        return userFile;
    }

    public enum FileType {
        IMAGE, DOC, ETC
    }



}
