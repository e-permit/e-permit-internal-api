package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.Credential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findOneBySerialNumber(String serialNumber);

    Optional<Credential> findFirstByOrderByPidDesc();

    Optional<Credential> findFirstByRevokedTrue();
}
