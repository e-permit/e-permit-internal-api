package epermit.data.utils;


import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.entities.Credential;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.CredentialRepository;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

public class MessageUtils {
    private final RestTemplate restTemplate;
    private final AuthorityRepository authorityRepository;
    private final IssuedCredentialRepository issuedCredentialRepository;
    private final CredentialRepository credentialRepository;

    public MessageUtils(RestTemplate restTemplate, AuthorityRepository authorityRepository,
            IssuedCredentialRepository issuedCredentialRepository,
            CredentialRepository credentialRepository) {
        this.restTemplate = restTemplate;
        this.authorityRepository = authorityRepository;
        this.credentialRepository = credentialRepository;
        this.issuedCredentialRepository = issuedCredentialRepository;
    }

    @SneakyThrows
    public boolean sendMesaage(String aud, String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(jwt, headers);
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        restTemplate.postForEntity(authority.get().getUri(), request, Boolean.class);
        return true;
    }

    public CommandResult handleCreate(Map<String, Object> claims) {
        Optional<Credential> credResult = credentialRepository.findOneBySerialNumber(claims.get("pmt").toString());
        if(!credResult.isPresent()){
            return CommandResult.fail("errorCode", "errorMessage");
        }
        return CommandResult.success();
    }

    public CommandResult handleRevoke(Map<String, Object> claims) {
        Optional<Credential> credResult = credentialRepository.findOneBySerialNumber(claims.get("serial_number").toString());
        if(!credResult.isPresent()){
            return CommandResult.fail("errorCode", "errorMessage");
        }
        return CommandResult.success();
    }

    public CommandResult handleFeedback(Map<String, Object> claims) {
        Optional<IssuedCredential> credResult = issuedCredentialRepository.findOneBySerialNumber(claims.get("serial_number").toString());
        if(!credResult.isPresent()){
            return CommandResult.fail("errorCode", "errorMessage");
        }
        return CommandResult.success();
    }

    public Boolean validatePermitId(String permitId) {
        return false;
    }
}



