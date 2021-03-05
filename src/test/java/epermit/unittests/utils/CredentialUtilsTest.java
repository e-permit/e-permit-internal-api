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
import epermit.common.PermitType;
import epermit.config.EPermitProperties;
import epermit.config.EPermitProperties.Issuer;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.data.entities.Permit;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedPermitRepository;
import epermit.data.utils.PermitUtil;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class CredentialUtilsTest {

    @Mock
    EPermitProperties properties;

    @Mock
    AuthorityRepository authorityRepository;

    @Mock
    IssuedPermitRepository issuedCredentialRepository;

    PermitUtil utils;

    @BeforeEach
    public void beforeEach() {
        Issuer issuer = new Issuer();
        issuer.setCode("TR");
        lenient().when(properties.getIssuer()).thenReturn(issuer);
        utils = new PermitUtil(authorityRepository, issuedCredentialRepository, properties);
    }

    @Test
    void serialNumberTest() {
        String serialNumber = utils.getSerialNumber("UA", PermitType.BILITERAL, 2021, 12345);
        Assertions.assertEquals(serialNumber, "TR-UA-2021-1-12345");
    }

    /*@Test
    void getFeedbackClaimsTest() {
        Permit cred = new Permit();
        cred.setIss("UA");
        cred.setSerialNumber("serialNumber");
        Map<String, Object> claims = utils.getFeedbackClaims(cred);
        assertEquals((int)claims.get("pmt"), 3);
        assertEquals(claims.get("serial_number").toString(), "serialNumber");
        assertEquals(claims.get("aud"), "UA");
    }*/
}
