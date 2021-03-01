package epermit.integrationtests.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import epermit.common.CustomPostgresContainer;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.IssuedCredentialRepository;
import epermit.data.services.IssuedCredentialServiceImpl;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class IssuedCredentialServiceIT {
    @Container
    public static PostgreSQLContainer postgreSQLContainer = CustomPostgresContainer.getInstance();

    @Autowired
    private IssuedCredentialServiceImpl credentialServiceImpl;

    @Autowired
    private IssuedCredentialRepository credentialRepository;

    @Test
    void pagingShouldRun(){
        for (long i = 0; i < 30; i++) {
            IssuedCredential cr = new IssuedCredential();
            cr.setClaims("claims");
            cr.setExp((long) 123);
            cr.setIat((long) 123);
            cr.setJws("claims");
            cr.setPid(123);
            cr.setPt(1);
            cr.setPy(2021);
            cr.setQrcode("claims");
            cr.setSub("claims");
            cr.setRevoked(false);
            cr.setSerialNumber("claims");
            cr.setUsed(false);
            credentialRepository.save(cr);
        }
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<epermit.core.issuedcredentials.IssuedCredentialDto> r = credentialServiceImpl.getAll(pageable);
        assertEquals(r.getContent().size(), 10);
        assertEquals(r.getTotalElements(), 30);
    }
}
