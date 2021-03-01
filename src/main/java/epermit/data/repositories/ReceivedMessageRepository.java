package epermit.data.repositories;

import org.springframework.stereotype.Repository;
import epermit.data.entities.ReceivedMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReceivedMessageRepository extends JpaRepository<ReceivedMessage, Long> {
     List<ReceivedMessage> findFirst10ByHandledFalse();
}
