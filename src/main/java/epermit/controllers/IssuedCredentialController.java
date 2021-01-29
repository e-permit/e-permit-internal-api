package epermit.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epermit.core.issuedcredentials.IssuedCredential;
import epermit.core.issuedcredentials.IssuedCredentialService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/issued_credentials")
@Slf4j
public class IssuedCredentialController {

    private final IssuedCredentialService credentialService;

    public IssuedCredentialController(IssuedCredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping()
    public ResponseEntity<Page<IssuedCredential>> getAll() {
        return new ResponseEntity<>(credentialService.getAll(null), HttpStatus.OK);
    }
    
}
