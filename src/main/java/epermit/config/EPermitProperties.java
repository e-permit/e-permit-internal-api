package epermit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "epermit")
public class EPermitProperties {

    private String keyPassword;

    private String tokenSecret;

    private Issuer issuer;

    @Getter
    @Setter
    public static class Issuer {
        private String code;

        private String title;
    
        private String uri;
    }
}




    /*private List<User> users = new ArrayList<>();

    @Getter
    public static class User {
        private String username;
        private String secret;
        private String role;
    }*/
