package epermit.common;

import lombok.Data;

@Data
public class MessageCreatedEvent {
    private String message;

    private String authorityUri;
}
