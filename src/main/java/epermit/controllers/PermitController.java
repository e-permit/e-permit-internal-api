package epermit.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import epermit.common.CommandResult;
import epermit.core.credentials.CredentialDto;
import epermit.core.credentials.CredentialService;

@RestController
@RequestMapping("/permits")
public class PermitController {

    private final CredentialService credentialService;

    public PermitController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping()
    public ResponseEntity<Page<CredentialDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(credentialService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CredentialDto getById(Long id) {
        return credentialService.getById(id);
    }

    @PatchMapping("/{id}/used")
    public CommandResult setUsed(Long id) {
        return credentialService.setUsed(id);
    }
}
