package epermit.data.utils;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.entities.VerifierQuota;
import epermit.data.entities.Permit;
import epermit.data.entities.IssuedPermit;
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

    /*public CommandResult handleCreate(Map<String, Object> claims) {
        Optional<Credential> credResult =
                credentialRepository.findOneBySerialNumber(claims.get("serial_number").toString());
        if (!credResult.isPresent()) {
            return CommandResult.fail("SERIAL_NUMBER_EXISTS", "The serial number exists");
        }
        Credential cred = new Credential();
        cred.setCn(claims.get("cn").toString());
        cred.setCreatedAt(OffsetDateTime.now());
        cred.setExp((long) claims.get("exp"));
        cred.setIat((long) claims.get("iat"));
        cred.setIss(claims.get("iss").toString());
        cred.setPid((int) claims.get("pid"));
        cred.setPy((int) claims.get("py"));
        cred.setPt((int) claims.get("pt"));
        cred.setSerialNumber(claims.get("serial_number").toString());
        cred.setSub(claims.get("sub").toString());
        cred.setClaims(claims.get("claims").toString());
        credentialRepository.save(cred);
        return CommandResult.success();
    }

    public CommandResult handleRevoke(Map<String, Object> claims) {
        Optional<Credential> credResult =
                credentialRepository.findOneBySerialNumber(claims.get("serial_number").toString());
        if (!credResult.isPresent()) {
            return CommandResult.fail("PERMIT_NOT_FOUND", "The permit not found");
        }
        Credential cred = credResult.get();
        credentialRepository.delete(cred);
        return CommandResult.success();
    }

    public CommandResult handleFeedback(Map<String, Object> claims) {
        Optional<IssuedCredential> credResult = issuedCredentialRepository
                .findOneBySerialNumber(claims.get("serial_number").toString());
        if (!credResult.isPresent()) {
            return CommandResult.fail("PERMIT_NOT_FOUND", "The permit not found");
        }
        IssuedCredential cred = credResult.get();
        cred.setUsed(true);
        cred.setUsedAt(OffsetDateTime.now());
        issuedCredentialRepository.save(cred);
        return CommandResult.success();
    }*/

    public Boolean validatePermitId(Map<String, Object> claims) {
        int pid = (int) claims.get("pid");
        Authority authority = authorityRepository.findByCode(claims.get("iss").toString()).get();
        Optional<VerifierQuota> quotaResult = authority.getVerifierQuotas().stream()
                .filter(x -> x.getYear() == (int) claims.get("py") && pid < x.getEndNumber()
                        && pid > x.getStartNumber() && x.getPermitType() == claims.get("pt"))
                .findAny();
        return quotaResult.isPresent();
    }
}


