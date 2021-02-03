package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.IssuedCredential;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IssuedCredentialRepository extends JpaRepository<IssuedCredential, Long> {
     IssuedCredential findByQrCode(String qrCode);
     IssuedCredential findBySerialNumber(String serialNumber);
}
