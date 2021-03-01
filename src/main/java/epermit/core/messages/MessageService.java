package epermit.core.messages;

import epermit.common.CommandResult;

public interface MessageService {
    CommandResult receive(ReceiveMessageInput input);
}
