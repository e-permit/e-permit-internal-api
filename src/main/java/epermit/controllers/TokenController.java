package epermit.controllers;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class TokenController {
    @PostMapping("/token")
    public String token() {
        return null;
    }
}
