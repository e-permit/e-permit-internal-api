package epermit.controllers;

import org.springframework.web.bind.annotation.PostMapping;

public class AuthController {
    @PostMapping("/token")
    public String signin() {
        return null;
    }
}
