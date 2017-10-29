package io.osoon.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author whiteship
 */
@Component
@ConfigurationProperties(prefix = "osoon")
@Getter @Setter
@NoArgsConstructor
public class OSoonProperties {

    @NotNull
    private String rememberMeKey;

    @NotNull
    private String cookieName;

    @NotNull
    private String uploadFileRootPath;

}
