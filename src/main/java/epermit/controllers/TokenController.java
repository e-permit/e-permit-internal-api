package epermit.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import epermit.dtos.ApiClientInput;
import epermit.dtos.ApiClientValidationResult;
import epermit.services.ApiClientServiceImpl;

@RestController
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private ApiClientServiceImpl apiClientService;
    @PostMapping(value = "/token")
    public ResponseEntity<String> post(@RequestBody ApiClientInput input) {
        try {
            ApiClientValidationResult r = apiClientService.validate(input);
            if(!r.isValid()){

            }
            return new ResponseEntity<>("doc.getId()", HttpStatus.OK);

        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
