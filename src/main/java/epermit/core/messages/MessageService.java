package epermit.core.messages;

import epermit.common.CommandResult;

public interface MessageService {
    CommandResult create(MessageInput input);
}
