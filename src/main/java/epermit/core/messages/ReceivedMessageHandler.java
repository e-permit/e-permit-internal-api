package epermit.core.messages;

public interface ReceivedMessageHandler {
    boolean execute(Long messageId);
}
