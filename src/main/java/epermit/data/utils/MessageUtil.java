package epermit.data.utils;

import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Optional;
import com.nimbusds.jose.util.Base64URL;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import epermit.commands.CreatePermitCommand;
import epermit.common.MessageBase;
import epermit.common.MessageCreatedEvent;
import epermit.common.MessageType;
import epermit.config.EPermitProperties;
import epermit.data.entities.Authority;
import epermit.data.entities.CreatedMessage;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.CreatedMessageRepository;
import epermit.messages.CreatePermitMessage;
import lombok.SneakyThrows;

public class MessageUtil {
    private final RestTemplate restTemplate;
    private final AuthorityRepository authorityRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final JwsUtil jwsUtil;
    private final CreatedMessageRepository createdMessageRepository;
    private final EPermitProperties props;


    public MessageUtil(RestTemplate restTemplate, AuthorityRepository authorityRepository,
            ApplicationEventPublisher eventPublisher, JwsUtil jwsUtil,
            CreatedMessageRepository createdMessageRepository, EPermitProperties props) {
        this.restTemplate = restTemplate;
        this.authorityRepository = authorityRepository;
        this.jwsUtil = jwsUtil;
        this.eventPublisher = eventPublisher;
        this.createdMessageRepository = createdMessageRepository;
        this.props = props;
    }

    @SneakyThrows
    public String getMessageId(String jws){
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(Base64.getUrlDecoder().decode(jws) );
        String messageId = Base64.getUrlEncoder().encodeToString(encodedhash);
        return messageId;
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

    public CreatePermitMessage getCreatePermitMessage(int pid, String serialNumber,
            CreatePermitCommand cmd) {
        CreatePermitMessage message = CreatePermitMessage.builder()
                .companyName(cmd.getCompanyName()).permitId(pid).permitType(cmd.getPermitType())
                .permitYear(cmd.getPermitYear()).plateNumber(cmd.getPlateNumber())
                .claims(cmd.getClaims()).serialNumber(serialNumber).build();
        setCommonClaims(message);
        message.setMessageType(MessageType.CREATE_KEY);
        return message;
    }

    private <T extends MessageBase> void setCommonClaims(T message){
        message.setIssuedAt(OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond());
        message.setIssuer(props.getIssuer().getCode());
    }

    public <T extends MessageBase> CreatedMessage getCreatedMessage(T message) {
        CreatedMessage messageEntity = new CreatedMessage();
        messageEntity.setIssuedFor(message.getIssuedFor());
        messageEntity.setMessage(jwsUtil.createJws(message));
        return messageEntity;
    }

    public <T extends MessageBase> void publish(T message) {
        Authority authority = authorityRepository.findByCode(message.getIssuedFor()).get();
        CreatedMessage messageEntity = getCreatedMessage(message);
        createdMessageRepository.save(messageEntity);
        MessageCreatedEvent event = new MessageCreatedEvent();
        event.setAuthorityUri(authority.getUri());
        event.setMessage(messageEntity.getMessage());
        eventPublisher.publishEvent(message);
    }
 
}


