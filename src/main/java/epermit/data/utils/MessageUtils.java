package epermit.data.utils;


import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

public class MessageUtils {
    private final RestTemplate restTemplate;
    private final AuthorityRepository authorityRepository;

    public MessageUtils(RestTemplate restTemplate, AuthorityRepository authorityRepository) {
        this.restTemplate = restTemplate;
        this.authorityRepository = authorityRepository;
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

    public CommandResult handleCreate(Map<String, Object> claims){
        // validate pid
        // create credential
         return CommandResult.fail("errorCode", "errorMessage");
    }

    public CommandResult handleRevoke(Map<String, Object> claims){
        return CommandResult.fail("errorCode", "errorMessage");
    }

    public CommandResult handleFeedback(Map<String, Object> claims){
        // get credential
        // if suitable delete it
        return CommandResult.fail("errorCode", "errorMessage");
    }
 
    public Boolean validatePermitId(String permitId) {
        return false;
    }
}
