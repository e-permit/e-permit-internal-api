package epermit.data.commandhandlers;

import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import com.google.gson.Gson;
import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import antlr.debug.MessageAdapter;
import epermit.commands.ReceiveMessageCommand;
import epermit.common.MessageProofResult;
import epermit.common.MessageResult;
import epermit.data.entities.ReceivedMessage;
import epermit.data.repositories.ReceivedMessageRepository;
import epermit.messages.CreateKeyMessage;
import epermit.messages.CreatePermitMessage;
import epermit.messages.CreateQuotaMessage;
import epermit.messages.PermitUsedMessage;
import epermit.messages.QuotaCreatedMessage;
import epermit.messages.RevokePermitMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveMessageCommandHandler
        implements Command.Handler<ReceiveMessageCommand, MessageResult> {
    private final Pipeline pipeline;
    private final ReceivedMessageRepository repository;

    public ReceiveMessageCommandHandler(ReceivedMessageRepository repository, Pipeline pipeline) {
        this.repository = repository;
        this.pipeline = pipeline;
    }

    @Override
    @Transactional
    public MessageResult handle(ReceiveMessageCommand cmd) {
        log.info("The message is recived"); // cmd.toString()
        MessageProofResult proofResult = validateMessageProof(cmd.getMessageJws());
        if(!proofResult.isValid()){
            return MessageResult.success("ackProof");
        }
        Command<MessageResult> m = getCommandMessage(cmd);
        MessageResult result = m.execute(pipeline);   
        ReceivedMessage message = new ReceivedMessage();
        message.setCreatedAt(OffsetDateTime.now());
        message.setIss("cmd.getIss()");
        if(result.getSucceed()){
            // create ack proof
            message.setAckProof(createAckProof(result)); 
        }
        
        repository.save(message);
        return result;
    }

    private String createAckProof(MessageResult result){
        return "";
    }

    private MessageProofResult validateMessageProof(String jws){
        MessageProofResult r = new MessageProofResult();
        return r;
    }

    private Command<MessageResult> getCommandMessage(ReceiveMessageCommand cmd) {
        Command<MessageResult> m = null;
        Gson gson = new Gson();
        
        /*switch (cmd.getMessageType()) {
            case CREATE_KEY:
                m = gson.fromJson(cmd.getMessageJws(), CreateKeyMessage.class);
                break;
            case CREATE_PERMIT:
                m = gson.fromJson(cmd.getMessage(), CreatePermitMessage.class);
                break;
            case CREATE_QUOTA:
                m = gson.fromJson(cmd.getMessage(), CreateQuotaMessage.class);
                break;
            case PERMIT_USED:
                m = gson.fromJson(cmd.getMessage(), PermitUsedMessage.class);
                break;
            case QUOTA_CREATED:
                m = gson.fromJson(cmd.getMessage(), QuotaCreatedMessage.class);
                break;
            case REVOKE_PERMIT:
                m = gson.fromJson(cmd.getMessage(), RevokePermitMessage.class);
                break;
        }*/
        return m;
    }
}
