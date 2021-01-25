package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    
}
