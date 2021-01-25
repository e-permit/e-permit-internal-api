package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.AuthorityQuota;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuthorityQuotaRepository extends JpaRepository<AuthorityQuota, Long> {
    
}
