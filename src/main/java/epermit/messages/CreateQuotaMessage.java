package epermit.messages;

import an.awesome.pipelinr.Command;
import epermit.common.MessageBase;
import epermit.common.MessageHandleResult;
import epermit.common.PermitType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CreateQuotaMessage extends MessageBase implements Command<MessageHandleResult> {

    private int quotaId;
    
    private int year;

    private PermitType permitType;

    private int startId;

    private int endId;
}
