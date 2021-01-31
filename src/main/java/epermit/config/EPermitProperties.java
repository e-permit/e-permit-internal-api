package epermit.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
@ConfigurationProperties(prefix = "epermit")
public class EPermitProperties {

    private String keyPassword;

    private List<User> users = new ArrayList<>();

    @Getter
    public static class User {
        private String username;
        private String secret;
        private String role;
    }
}
