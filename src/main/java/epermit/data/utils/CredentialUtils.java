package epermit.data.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import epermit.config.EPermitProperties;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityQuota;
import epermit.data.entities.Credential;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.CredentialRepository;
import epermit.data.repositories.IssuedCredentialRepository;

@Component
public class CredentialUtils {

    private final AuthorityRepository authorityRepository;
    private final IssuedCredentialRepository issuedCredentialRepository;
    private final CredentialRepository credentialRepository;

    public CredentialUtils(AuthorityRepository authorityRepository,
            IssuedCredentialRepository issuedCredentialRepository, 
            EPermitProperties props,
            CredentialRepository credentialRepository) {
        this.authorityRepository = authorityRepository;
        this.issuedCredentialRepository = issuedCredentialRepository;
        this.credentialRepository = credentialRepository;
    }

    public Integer getPermitId(String aud, int py, int pt) {
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        Optional<IssuedCredential> revokedCred =
                issuedCredentialRepository.findFirstByRevokedTrue();
        if (revokedCred.isPresent()) {
            int nextPid = revokedCred.get().getPid();
            issuedCredentialRepository.delete(revokedCred.get());
            return nextPid;
        }

        Optional<AuthorityQuota> quotaResult =
                authority.get().getQuotas().stream().filter(x -> x.getYear() == py && x.isActive()
                        && x.getPermitType() == pt && x.isVehicleOwner()).findFirst();
        if (quotaResult.isPresent()) {
            AuthorityQuota quota = quotaResult.get();
            int nextPid = quota.getCurrentNumber() + 1;
            quota.setCurrentNumber(nextPid);
            if (quota.getCurrentNumber() == quota.getEndNumber()) {
                quota.setActive(false);
            }
            authorityRepository.save(authority.get());
            return nextPid;
        }
        return null;
    }

    public Map<String, Object> getPermitQrCodeClaims(CreateIssuedCredentialInput input, int pid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("aud", input.getAud());
        claims.put("sub", input.getSub());
        claims.put("pt", input.getPt());
        claims.put("py", input.getPy());
        claims.put("pid", pid);
        claims.put("cn", input.getCn());
        return claims;
    }

    public Map<String, Object> getPermitClaims(CreateIssuedCredentialInput input, int pid) {
        Map<String, Object> claims = new HashMap<>();
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, ChronoUnit.YEARS);
        claims.put("aud", input.getAud());
        claims.put("pmt", 1);
        claims.put("iat", iat);
        claims.put("exp", exp);
        claims.put("sub", input.getSub());
        claims.put("pt", input.getPt());
        claims.put("py", input.getPy());
        claims.put("pid", pid);
        claims.put("cn", input.getCn());
        input.getClaims().forEach((k, v) -> {
            claims.put(k, v);
        });
        return claims;    
    }

    public Map<String, Object> getRevokeClaims(IssuedCredential cred) {
        Map<String, Object> claims = new HashMap<>();
        /*claims.put("aud", input.getAud());
        claims.put("sub", input.getSub());
        claims.put("pt", input.getPt());
        claims.put("py", input.getPy());
        claims.put("pid", pid);
        claims.put("cn", input.getCn());*/
        return claims;
    }

    public Map<String, Object> getFeedbackClaims(Credential cred) {
        Map<String, Object> claims = new HashMap<>();
        /*claims.put("aud", input.getAud());
        claims.put("sub", input.getSub());
        claims.put("pt", input.getPt());
        claims.put("py", input.getPy());
        claims.put("pid", pid);
        claims.put("cn", input.getCn());*/
        return claims;
    }
}
