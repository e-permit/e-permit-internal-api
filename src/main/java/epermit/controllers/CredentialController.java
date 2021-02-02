package epermit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CredentialController {
    @GetMapping("/credentials")
    public String hello() {
        return "Hello";
    }

}
