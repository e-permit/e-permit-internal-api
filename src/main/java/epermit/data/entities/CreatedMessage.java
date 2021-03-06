package epermit.data.entities;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import epermit.common.CreatedMessageState;
import epermit.common.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "created_messages")
public class CreatedMessage {
    @Id
    @GeneratedValue
    private long id;
    
    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "issued_for", nullable = false)
    private String issuedFor;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "message", nullable = false, length=10000)
    private String message;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "state", nullable = false)
    private CreatedMessageState state;

    @Column(name = "succeed", nullable = false)
    private Boolean succeed;

    @Column(name = "ack", nullable = false, length=5000)
    private String ack;

    @Column(name = "error_code", nullable = true)
    private Boolean errorCode;

    @Column(name = "locked_at", nullable = false)
    private OffsetDateTime lockedAt;

    @Column(name = "handled_at", nullable = false)
    private OffsetDateTime sendedAt;
}