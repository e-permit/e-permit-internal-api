package epermit.data.entities;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import epermit.core.messages.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@Entity
@Table(name = "received_messages")
public class ReceivedMessage {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "iss", nullable = false)
    private String iss;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "message", nullable = false, length=10000)
    private String message;

    @Column(name = "proof", nullable = false, length=500)
    private String proof;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
