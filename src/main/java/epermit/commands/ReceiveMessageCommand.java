package epermit.commands;

import an.awesome.pipelinr.Command;
import epermit.common.MessageResult;
import lombok.Data;

@Data
public class ReceiveMessageCommand implements Command<MessageResult> {
    private String messageJws;
    /*private MessageType messageType;

    private String message;

    private String proof;*/
}
