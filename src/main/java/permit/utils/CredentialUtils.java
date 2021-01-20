package permit.utils;

import java.util.Date;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSProvider;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import permit.dtos.CreatePermitInput;

public class CredentialUtils {
    @Autowired
    private KeyUtils keyUtils;

    @Value("")
    private String issuer;

    public String createJws(CreatePermitInput input) throws Exception {
        JWSSigner signer = new ECDSASigner(keyUtils.getCurrentKey());
        Date iat = new Date(new Date().getTime() + 60 * 1000);
        Date exp = new Date(new Date().getTime() + 60 * 1000);
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder().subject(input.getSub()).issuer(issuer)
                .expirationTime(exp).issueTime(iat).audience(input.getAud());
        claimsSet.claim("pid", input.getPid());
        claimsSet.claim("py", input.getPy());
        claimsSet.claim("pt", input.getPt());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyUtils.getCurrentKey().getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet.build());
        signedJWT.sign(signer);
        String jwt = signedJWT.serialize();
        return jwt;
    }
}
