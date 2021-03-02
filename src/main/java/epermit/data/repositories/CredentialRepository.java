package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.Permit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CredentialRepository extends JpaRepository<Permit, Long> {
    Optional<Permit> findOneBySerialNumber(String serialNumber);

    Optional<Permit> findFirstByOrderByPidDesc();

    Optional<Permit> findFirstByRevokedTrue();
}
