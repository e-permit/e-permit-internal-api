package epermit.unittests.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import epermit.config.EPermitProperties;
import epermit.config.EPermitProperties.Issuer;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.data.entities.Credential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;
import epermit.data.utils.CredentialUtils;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class CredentialUtilsTest {

    @Mock
    EPermitProperties properties;

    @Mock
    AuthorityRepository authorityRepository;

    @Mock
    IssuedCredentialRepository issuedCredentialRepository;

    CredentialUtils utils;

    @BeforeEach
    public void beforeEach() {
        Issuer issuer = new Issuer();
        issuer.setCode("TR");
        lenient().when(properties.getIssuer()).thenReturn(issuer);
        utils = new CredentialUtils(authorityRepository, issuedCredentialRepository, properties);
    }

    @Test
    void serialNumberTest() {
        CreateIssuedCredentialInput input = new CreateIssuedCredentialInput();
        input.setAud("UA");
        input.setPt(1);
        input.setPy(2021);
        String serialNumber = utils.getSerialNumber(input, 12345);
        Assertions.assertEquals(serialNumber, "TR-UA-2021-1-12345");
    }

    @Test
    void getFeedbackClaimsTest() {
        Credential cred = new Credential();
        cred.setIss("UA");
        cred.setSerialNumber("serialNumber");
        Map<String, Object> claims = utils.getFeedbackClaims(cred);
        assertEquals((int)claims.get("pmt"), 3);
        assertEquals(claims.get("serial_number").toString(), "serialNumber");
        assertEquals(claims.get("aud"), "UA");
    }
}
