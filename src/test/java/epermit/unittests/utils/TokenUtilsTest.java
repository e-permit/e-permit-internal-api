package epermit.unittests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.SignedJWT;

import org.junit.jupiter.api.Test;

import epermit.data.utils.*;
import lombok.SneakyThrows;

class TokenUtilsTest {
    /*@Test
    @SneakyThrows
    void TokenShouldBeCreated() {
        ECKey ecJWK = new ECKeyGenerator(Curve.P_256).keyID("1").generate();
        TokenUtils utils = new TokenUtils(ecJWK, "tr");
        ApiClientValidationResult client = new ApiClientValidationResult();
        client.setClientId("abc");
        client.setExpiration(60);
        client.setScope("app");
        String jwt = utils.createToken(client);
        System.out.println(jwt);
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new ECDSAVerifier(ecJWK.toPublicJWK());
        assertTrue(signedJWT.verify(verifier));
        assertEquals(client.getClientId(), signedJWT.getJWTClaimsSet().getSubject());
        assertEquals("tr", signedJWT.getJWTClaimsSet().getIssuer());
        assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
    }*/
    
/*
 * @Override public CommandResult create(String jwt) { // validate jwt // validate info // create
 * return CommandResult.success(); }
 */

/*
 * @Override public CommandResult delete(String serialNumber, String jwt) { // validate jwt and info
 * Optional<Credential> credResult = repository.findOneBySerialNumber(serialNumber); if
 * (!credResult.isPresent()) { return CommandResult.fail("CREDENTIAL_NOT_FOUND",
 * "Not found credential for serial number: " + serialNumber); }
 * transactionTemplate.executeWithoutResult(action->{ Credential cred = credResult.get();
 * repository.delete(cred); }); return CommandResult.success(); }
 */

/*
 * public Integer getPermitId(String aud, int py, int pt) { Optional<Authority> authority =
 * authorityRepository.findByCode(aud); Optional<IssuedCredential> revokedCred =
 * issuedCredentialRepository.findFirstByRevokedTrue(); if (revokedCred.isPresent()) { int nextPid =
 * revokedCred.get().getPid(); issuedCredentialRepository.delete(revokedCred.get()); return nextPid;
 * }
 * 
 * Optional<AuthorityQuota> quotaResult = authority.get().getQuotas().stream().filter(x ->
 * x.getYear() == py && x.isActive() && x.getPermitType() == pt && x.isVehicleOwner()).findFirst();
 * if (quotaResult.isPresent()) { AuthorityQuota quota = quotaResult.get(); int nextPid =
 * quota.getCurrentNumber() + 1; quota.setCurrentNumber(nextPid); if (quota.getCurrentNumber() ==
 * quota.getEndNumber()) { quota.setActive(false); } authorityRepository.save(authority.get());
 * return nextPid; } return null; }
 * 
 * @SneakyThrows public String createPermitQrCode(CreateIssuedCredentialInput input, int pid) { //
 * LocalDateTime.now(ZoneOffset.UTC).plusYears(1).plusMonths(1); // Date iat = new Date(); // Date
 * exp = new Date(new Date().getTime() + 24 * 60 * 60 * 1000); JWTClaimsSet.Builder claimsSet = new
 * JWTClaimsSet.Builder().issuer(props.getIssuer().getCode())
 * .audience(input.getAud()).subject(input.getSub()).claim("py", input.getPy()) .claim("pt",
 * input.getPt()).claim("cn", input.getCn()).claim("pid", pid); JWSHeader header = new
 * JWSHeader.Builder(JWSAlgorithm.ES256) .keyID(keyUtils.GetKey().getKeyID()).build(); SignedJWT
 * signedJWT = new SignedJWT(header, claimsSet.build()); JWSSigner signer = new
 * ECDSASigner(keyUtils.GetKey()); signedJWT.sign(signer); String jwt = signedJWT.serialize();
 * return jwt; }
 */

/*
 * JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode())
 * .expirationTime(Date.from(exp)).issueTime(Date.from(iat))
 * .audience(input.getAud()).subject(input.getSub()).claim("py", input.getPy()) .claim("pt",
 * input.getPt()).claim("cn", input.getCn()).claim("pid", pid); input.getClaims().forEach((k, v) ->
 * { claimsSet.claim(k, v); }); JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
 * .keyID(keyUtils.GetKey().getKeyID()).build(); SignedJWT signedJWT = new SignedJWT(header,
 * claimsSet.build()); JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
 * signedJWT.sign(signer); String jwt = signedJWT.serialize(); return jwt;
 */

/*
 * @SneakyThrows public String createPermitJws(CreateIssuedCredentialInput input, int pid) { Instant
 * iat = Instant.now(); Instant exp = iat.plus(1, ChronoUnit.YEARS); JWTClaimsSet.Builder claimsSet
 * = new JWTClaimsSet.Builder().issuer(props.getIssuer().getCode())
 * .expirationTime(Date.from(exp)).issueTime(Date.from(iat))
 * .audience(input.getAud()).subject(input.getSub()).claim("py", input.getPy()) .claim("pt",
 * input.getPt()).claim("cn", input.getCn()).claim("pid", pid); input.getClaims().forEach((k, v) ->
 * { claimsSet.claim(k, v); }); JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
 * .keyID(keyUtils.GetKey().getKeyID()).build(); SignedJWT signedJWT = new SignedJWT(header,
 * claimsSet.build()); JWSSigner signer = new ECDSASigner(keyUtils.GetKey());
 * signedJWT.sign(signer); String jwt = signedJWT.serialize(); return jwt; }
 */


/*
 * private ECKey ecKey;
 * 
 * private String issuer;
 * 
 * public CredentialUtils(ECKey ecKey, String issuer) { this.ecKey = ecKey; this.issuer = issuer; }
 * 
 * public String createJws(CreatePermitInput input) throws Exception { JWSSigner signer = new
 * ECDSASigner(ecKey); Date iat = new Date(); Date exp = new Date(new Date().getTime() + 60 * 60 *
 * 1000); JWTClaimsSet.Builder claimsSet = new
 * JWTClaimsSet.Builder().subject(input.getSub()).issuer(issuer)
 * .expirationTime(exp).issueTime(iat).audience(input.getAud()); claimsSet.claim("pid",
 * input.getPid()); claimsSet.claim("py", input.getPy()); claimsSet.claim("pt", input.getPt());
 * JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(ecKey.getKeyID()).build();
 * SignedJWT signedJWT = new SignedJWT(header, claimsSet.build()); signedJWT.sign(signer); String
 * jwt = signedJWT.serialize(); return jwt; }
 */
}
