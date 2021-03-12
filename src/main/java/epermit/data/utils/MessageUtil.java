package epermit.data.utils;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import epermit.data.entities.IssuedPermit;
import epermit.data.entities.Permit;
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
    public boolean sendMesaage(String aud, String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(jwt, headers);
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        restTemplate.postForEntity(authority.get().getUri(), request, Boolean.class);
        return true;
    }

    public CreatePermitMessage convertPermitToMessage(IssuedPermit permit) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        CreatePermitMessage message =
                CreatePermitMessage.builder().companyName(permit.getCompanyName())
                        .permitId(permit.getPermitId()).permitType(permit.getPermitType())
                        .permitYear(permit.getPermitYear()).plateNumber(permit.getPlateNumber())
                        .claims(gson.fromJson(permit.getClaims(), type))
                        .serialNumber(permit.getSerialNumber()).build();
        setCommonClaims(message, permit.getIssuedFor(), MessageType.CREATE_PERMIT);
        return message;
    }

    public Permit convertMessageToPermit(CreatePermitMessage m) {
        Gson gson = new Gson();
        Permit permit = new Permit();
        permit.setClaims(gson.toJson(m.getClaims()));
    
        return permit;
     }


    public <T extends MessageBase> void publish(T message) {
        Authority authority = authorityRepository.findByCode(message.getIssuedFor()).get();
        CreatedMessage messageEntity = messageToEntity(message);
        createdMessageRepository.save(messageEntity);
        MessageCreatedEvent event = new MessageCreatedEvent();
        event.setAuthorityUri(authority.getUri());
        event.setMessage(messageEntity.getMessage());
        eventPublisher.publishEvent(message);
    }

    private <T extends MessageBase> void setCommonClaims(T message, String issuedFor,
            MessageType messageType) {
        message.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond());
        message.setIssuer(props.getIssuer().getCode());
        message.setIssuedFor(issuedFor);
        message.setMessageType(messageType);
        message.setMessageId(UUID.randomUUID().toString());
    }

    private <T extends MessageBase> CreatedMessage messageToEntity(T message) {
        CreatedMessage messageEntity = new CreatedMessage();
        messageEntity.setIssuedFor(message.getIssuedFor());
        messageEntity.setMessage(jwsUtil.createJws(message));
        return messageEntity;
    }

}









    /*@SneakyThrows
    public String getMessageId(String jws) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(Base64.getUrlDecoder().decode(jws));
        String messageId = Base64.getUrlEncoder().encodeToString(encodedhash);
        return messageId;
    }*/