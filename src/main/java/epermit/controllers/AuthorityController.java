package epermit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epermit.core.aurthorities.AuthorityDto;
import epermit.core.aurthorities.AuthorityService;

@RestController
@RequestMapping("/authorities")

public class AuthorityController {
    private final AuthorityService authorityHandler;

    public AuthorityController(AuthorityService authorityHandler) {
        this.authorityHandler = authorityHandler;
    }

    @GetMapping()
    public ResponseEntity<List<AuthorityDto>> getAll() {
        return new ResponseEntity<>(authorityHandler.getAll(), HttpStatus.OK);
    }
    /*
     * @PostMapping(value = "/token") public ResponseEntity<String>
     * post(@RequestBody ApiClientInput input) { try { ApiClientValidationResult r =
     * apiClientService.validate(input); if (!r.isValid()) {
     * 
     * } return new ResponseEntity<>("doc.getId()", HttpStatus.OK);
     * 
     * } catch (Exception exception) { logger.error(exception.getMessage(),
     * exception); return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); } }
     */
}
