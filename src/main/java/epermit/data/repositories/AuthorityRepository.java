package epermit.data.repositories;


import org.springframework.stereotype.Repository;
import epermit.data.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByCode(String code);
}
