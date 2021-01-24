package epermit.repositories;


import org.springframework.stereotype.Repository;
import epermit.entities.ApiClient;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ApiClientRepository extends JpaRepository<ApiClient, Long> {
    ApiClient findByClientId(String clientId);
}
