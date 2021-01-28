package epermit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epermit.core.aurthorities.Authority;
import epermit.core.aurthorities.AuthorityService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/authorities")
@Slf4j
public class AuthorityController {
    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping()
    public ResponseEntity<List<Authority>> getAll() {
        return new ResponseEntity<>(authorityService.getAll(), HttpStatus.OK);
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
