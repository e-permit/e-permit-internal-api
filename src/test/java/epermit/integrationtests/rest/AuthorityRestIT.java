package epermit.integrationtests.rest;


import java.util.Date;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import epermit.common.CustomPostgresContainer;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AuthorityRestIT.Initializer.class)
@ActiveProfiles("test")
public class AuthorityRestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = CustomPostgresContainer.getInstance();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    "epermit.jksfile=" + "PROPERTY_FIRST_VALUE");
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl());
            // TestPropertyValues.of("epermit.jksfile=abc").applyTo(applicationContext);
        }
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        Authority a = new Authority();
        a.setCode("tr");
        a.setCreatedAt(new Date());
        a.setName("name");
        a.setUri("permitUri");
        authorityRepository.save(a);
        System.out.println(authorityRepository.count());
        /*
         * assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/authorities",
         * String.class)) .contains("Hello, World");
         */
    }
}
