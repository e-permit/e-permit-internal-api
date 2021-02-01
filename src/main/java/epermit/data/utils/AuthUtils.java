package epermit.data.utils;

import java.util.Date;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;
import epermit.config.EPermitProperties;

@Component
public class AuthUtils {
    private EPermitProperties props;

    public AuthUtils(EPermitProperties props) {
        this.props = props;
    }
    // Create token
    // Validate token

    /*public String createToken() throws Exception {
        JWSSigner signer = new ECDSASigner(ecKey);
        Date iat = new Date();
        Date exp = new Date(new Date().getTime() + r.getExpiration() * 1000);
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder().subject(r.getClientId()).issuer(props.getIssuer().getCode())
                .expirationTime(exp).issueTime(iat).audience(props.getIssuer().getCode());
        claimsSet.claim("scope", r.getScope());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(ecKey.getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }*/
}
