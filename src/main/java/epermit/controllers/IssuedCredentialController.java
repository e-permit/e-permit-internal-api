package epermit.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import epermit.common.CommandResult;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.core.issuedcredentials.IssuedCredentialDto;
import epermit.core.issuedcredentials.IssuedCredentialService;

@RestController
@RequestMapping("/issued_credentials")
public class IssuedCredentialController {

    private final IssuedCredentialService credentialService;

    public IssuedCredentialController(IssuedCredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping()
    public ResponseEntity<Page<IssuedCredentialDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(credentialService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public IssuedCredentialDto getById(Long id) {
        return credentialService.getById(id);
    }

    @PostMapping()
    public CommandResult post(@RequestBody CreateIssuedCredentialInput input) {
        return credentialService.create(input);
    }

    @PatchMapping("/{id}/send")
    public CommandResult send(Long id) {
        return credentialService.send(id);
    }

    @PatchMapping("/{id}/revoke")
    public CommandResult revoke(Long id) {
        return credentialService.revoke(id);
    }

    // from verifier
    @PatchMapping("/{id}/setused")
    public CommandResult setUsed(Long id) {
        return credentialService.setUsed(id);
    }
    
}
