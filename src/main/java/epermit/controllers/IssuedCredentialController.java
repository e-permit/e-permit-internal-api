package epermit.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epermit.core.issuedcredentials.IssuedCredentialDto;
import epermit.core.issuedcredentials.IssuedCredentialService;

@RestController
@RequestMapping("/issued_credentials")
public class IssuedCredentialController {

    private final IssuedCredentialService credentialHandler;

    public IssuedCredentialController(IssuedCredentialService credentialHandler) {
        this.credentialHandler = credentialHandler;
    }

    @GetMapping()
    public ResponseEntity<Page<IssuedCredentialDto>> getAll() {
        return new ResponseEntity<>(credentialHandler.getAll(null), HttpStatus.OK);
    }
    
}
