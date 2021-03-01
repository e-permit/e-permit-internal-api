package epermit.data.repositories;

import org.springframework.stereotype.Repository;
import epermit.data.entities.CreatedMessage;
import epermit.core.messages.CreatedMessageState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CreatedMessageRepository extends JpaRepository<CreatedMessage, Long> {
    List<CreatedMessage> findFirst10ByState(CreatedMessageState state);
}
