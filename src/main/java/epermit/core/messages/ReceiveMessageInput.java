package epermit.core.messages;

import lombok.Data;

@Data
public class ReceiveMessageInput {
    private String message;

    private String jwt;
}
