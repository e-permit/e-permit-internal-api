package epermit.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import epermit.dtos.ApiClientInput;
import epermit.dtos.ApiClientValidationResult;
import epermit.entities.ApiClient;
import epermit.repositories.ApiClientRepository;


public class ApiClientServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientServiceImpl.class);

    @Autowired
    private ApiClientRepository apiClientRepository;

    public ApiClientValidationResult validate(ApiClientInput input) throws NoSuchAlgorithmException {
        ApiClient c = apiClientRepository.findByClientId(input.getClientId());
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getClientSecret().getBytes());
        String clientSecretHash = Base64.getEncoder().encodeToString(hashBytes);
        if (!c.getClientSecret().equals(clientSecretHash)) {
            logger.info("client_secret_doesnt_match");
            return ApiClientValidationResult.error("client_secret_doesnt_match");
        }
        return ApiClientValidationResult.success(c.getClientId(), c.getScope(), c.getExpiration());
    }
}
