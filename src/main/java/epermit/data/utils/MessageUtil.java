package epermit.data.utils;

import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.common.MessageBase;
import epermit.data.entities.Authority;
import epermit.data.entities.CreatedMessage;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.CreatedMessageRepository;
import lombok.SneakyThrows;

public class MessageUtil {
    private final RestTemplate restTemplate;
    private final AuthorityRepository authorityRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final JwsUtil jwsUtil;
    private final CreatedMessageRepository createdMessageRepository;


    public MessageUtil(RestTemplate restTemplate, AuthorityRepository authorityRepository,
            ApplicationEventPublisher eventPublisher, JwsUtil jwsUtil,
            CreatedMessageRepository createdMessageRepository) {
        this.restTemplate = restTemplate;
        this.authorityRepository = authorityRepository;
        this.jwsUtil = jwsUtil;
        this.eventPublisher = eventPublisher;
        this.createdMessageRepository = createdMessageRepository;
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

    public <T extends MessageBase> void publish(T message) {
        CreatedMessage messageEntity = new CreatedMessage();
        messageEntity.setAud(message.getAudience());
        messageEntity.setMessage(jwsUtil.createJws(message));
        createdMessageRepository.save(messageEntity);
        eventPublisher.publishEvent(message);
    }

    /*
     * public CommandResult handleCreate(Map<String, Object> claims) { Optional<Credential>
     * credResult =
     * credentialRepository.findOneBySerialNumber(claims.get("serial_number").toString()); if
     * (!credResult.isPresent()) { return CommandResult.fail("SERIAL_NUMBER_EXISTS",
     * "The serial number exists"); } Credential cred = new Credential();
     * cred.setCn(claims.get("cn").toString()); cred.setCreatedAt(OffsetDateTime.now());
     * cred.setExp((long) claims.get("exp")); cred.setIat((long) claims.get("iat"));
     * cred.setIss(claims.get("iss").toString()); cred.setPid((int) claims.get("pid"));
     * cred.setPy((int) claims.get("py")); cred.setPt((int) claims.get("pt"));
     * cred.setSerialNumber(claims.get("serial_number").toString());
     * cred.setSub(claims.get("sub").toString()); cred.setClaims(claims.get("claims").toString());
     * credentialRepository.save(cred); return CommandResult.success(); }
     * 
     * public CommandResult handleRevoke(Map<String, Object> claims) { Optional<Credential>
     * credResult =
     * credentialRepository.findOneBySerialNumber(claims.get("serial_number").toString()); if
     * (!credResult.isPresent()) { return CommandResult.fail("PERMIT_NOT_FOUND",
     * "The permit not found"); } Credential cred = credResult.get();
     * credentialRepository.delete(cred); return CommandResult.success(); }
     * 
     * public CommandResult handleFeedback(Map<String, Object> claims) { Optional<IssuedCredential>
     * credResult = issuedCredentialRepository
     * .findOneBySerialNumber(claims.get("serial_number").toString()); if (!credResult.isPresent())
     * { return CommandResult.fail("PERMIT_NOT_FOUND", "The permit not found"); } IssuedCredential
     * cred = credResult.get(); cred.setUsed(true); cred.setUsedAt(OffsetDateTime.now());
     * issuedCredentialRepository.save(cred); return CommandResult.success(); }
     */


}


