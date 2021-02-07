package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.IssuedCredential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IssuedCredentialRepository extends JpaRepository<IssuedCredential, Long> {
     Optional<IssuedCredential> findOneBySerialNumber(String serialNumber);

     Optional<IssuedCredential> findFirstByOrderByPidDesc();

     Optional<IssuedCredential> findFirstByRevokedTrue();
}
