package epermit.data.repositories;

import org.springframework.stereotype.Repository;
import epermit.data.entities.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
     //Key findFirstByOrderByIdDesc();
     @Query(value = "select from keys where enabled = true")
     Key getEnabled();

     Key findByKid(String kid);
}

