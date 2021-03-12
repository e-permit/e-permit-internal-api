package epermit.data.commandhandlers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.transaction.Transactional;
import com.google.gson.Gson;
import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import epermit.commands.ReceiveMessageCommand;
import epermit.common.JwsValidationResult;
import epermit.common.MessageHandleResult;
import epermit.common.MessageResult;
import epermit.common.MessageType;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;
import epermit.data.utils.JwsUtil;
import epermit.data.utils.MessageUtil;
import epermit.messages.CreateKeyMessage;
import epermit.messages.CreatePermitMessage;
import epermit.messages.CreateQuotaMessage;
import epermit.messages.PermitUsedMessage;
import epermit.messages.QuotaCreatedMessage;
import epermit.messages.RevokePermitMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveMessageCommandHandler
        implements Command.Handler<ReceiveMessageCommand, String> {
    private final Pipeline pipeline;
    private final ReceivedMessageRepository repository;
    private final JwsUtil jwsUtil;
    private final MessageUtil messageUtil;

    public ReceiveMessageCommandHandler(ReceivedMessageRepository repository, Pipeline pipeline,
            JwsUtil jwsUtil, MessageUtil messageUtil) {
        this.repository = repository;
        this.pipeline = pipeline;
        this.jwsUtil = jwsUtil;
        this.messageUtil = messageUtil;
    }

    @Override
    @Transactional
    @SneakyThrows
    public String handle(ReceiveMessageCommand cmd) {
        String resultCode;
        log.info("The message is recived");
        MessageType messageType = jwsUtil.getClaim(cmd.getMessageJws(), "message_type");
        JwsValidationResult validationResult = jwsUtil.validateJws(cmd.getMessageJws());
        if (validationResult.isValid()) {
            Command<MessageHandleResult> m = getMessageCommand(cmd.getMessageJws(), messageType);
            resultCode = m.execute(pipeline).getResultCode();
        } else {
            resultCode = validationResult.getErrorCode();
        }

        String issuer = jwsUtil.getClaim(cmd.getMessageJws(), "issuer");
        String audience = jwsUtil.getClaim(cmd.getMessageJws(), "issued_for");
        String messageId = jwsUtil.getClaim(cmd.getMessageJws(), "message_id");
        MessageResult result = new MessageResult();
        result.setResultCode(resultCode);
        result.setMessageId(messageId);
        result.setIssuer(audience);
        result.setAudience(issuer);
        result.setIssuedAt(OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond());
        String resultJws = jwsUtil.createJws(result);
        ReceivedMessage message = new ReceivedMessage();
        message.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        message.setAckJws(resultJws);
        message.setIss(issuer);
        message.setMessageType(messageType);
        message.setJws(cmd.getMessageJws());
        repository.save(message);
        return resultJws;
    }

    private Command<MessageHandleResult> getMessageCommand(String jws, MessageType messageType) {
        Command<MessageHandleResult> m = null;
        Gson gson = new Gson();
        switch (messageType) {
            case CREATE_KEY:
                m = gson.fromJson(jws, CreateKeyMessage.class);
                break;
            case CREATE_PERMIT:
                m = gson.fromJson(jws, CreatePermitMessage.class);
                break;
            case CREATE_QUOTA:
                m = gson.fromJson(jws, CreateQuotaMessage.class);
                break;
            case PERMIT_USED:
                m = gson.fromJson(jws, PermitUsedMessage.class);
                break;
            case QUOTA_CREATED:
                m = gson.fromJson(jws, QuotaCreatedMessage.class);
                break;
            case REVOKE_PERMIT:
                m = gson.fromJson(jws, RevokePermitMessage.class);
                break;
        }

        return m;
    }
}
