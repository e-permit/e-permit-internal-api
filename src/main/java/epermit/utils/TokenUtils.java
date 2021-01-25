package epermit.utils;

import java.util.Date;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


public class TokenUtils {
    private ECKey ecKey;

    private String issuer;

    public TokenUtils(ECKey ecKey, String issuer) {
        this.ecKey = ecKey;
        this.issuer = issuer;
    }

    public String createToken(ApiClientValidationResult r) throws Exception {
        JWSSigner signer = new ECDSASigner(ecKey);
        Date iat = new Date();
        Date exp = new Date(new Date().getTime() + r.getExpiration() * 1000);
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder().subject(r.getClientId()).issuer(issuer)
                .expirationTime(exp).issueTime(iat).audience(issuer);
        claimsSet.claim("scope", r.getScope());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(ecKey.getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }
}
