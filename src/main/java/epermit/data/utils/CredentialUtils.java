package epermit.data.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;
import epermit.common.PermitType;
import epermit.config.EPermitProperties;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuedCredential;
import epermit.data.entities.IssuerQuota;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;

@Component
public class CredentialUtils {

    private final AuthorityRepository authorityRepository;
    private final IssuedCredentialRepository issuedCredentialRepository;
    private EPermitProperties props;

    public CredentialUtils(AuthorityRepository authorityRepository,
            IssuedCredentialRepository issuedCredentialRepository, EPermitProperties props) {
        this.authorityRepository = authorityRepository;
        this.issuedCredentialRepository = issuedCredentialRepository;
        this.props = props;
    }

    public Integer getPermitId(String aud, int py, PermitType pt) {
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        Optional<IssuedCredential> revokedCred =
                issuedCredentialRepository.findFirstByRevokedTrue();
        if (revokedCred.isPresent()) {
            int nextPid = revokedCred.get().getPid();
            issuedCredentialRepository.delete(revokedCred.get());
            return nextPid;
        }

        Optional<IssuerQuota> quotaResult = authority.get().getIssuerQuotas().stream()
                .filter(x -> x.getYear() == py && x.isActive() && x.getPermitType() == pt)
                .findFirst();
        if (quotaResult.isPresent()) {
            IssuerQuota quota = quotaResult.get();
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
        claims.put("sub", input.getPlateNumber());
        claims.put("pt", input.getPermitType().getCode());
        claims.put("py", input.getPermitYear());
        claims.put("pid", pid);
        claims.put("cn", input.getCompanyName());
        return claims;
    }

    public String getSerialNumber(String aud, PermitType pt, Integer py, long pid) {
        StringJoiner joiner = new StringJoiner("-");
        String serialNumber =
                joiner.add(props.getIssuer().getCode()).add(aud).add(Integer.toString(py))
                        .add(Integer.toString(pt.getCode())).add(Long.toString(pid)).toString();
        return serialNumber;
    }
    
    
    
    /*public Map<String, Object> getPermitClaims(CreateIssuedCredentialInput input, int pid) {
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
        claims.put("serial_number", getSerialNumber(input, pid));
        input.getClaims().forEach((k, v) -> {
            claims.put(k, v);
        });
        return claims;
    }

    public Map<String, Object> getRevokeClaims(IssuedCredential cred) {
        Map<String, Object> claims = new HashMap<>();
        Instant iat = Instant.now();
        Instant exp = iat.plus(1, ChronoUnit.YEARS);
        claims.put("pmt", 2);
        claims.put("aud", cred.getAud());
        claims.put("iat", iat);
        claims.put("exp", exp);
        claims.put("serial_number", cred.getSerialNumber());
        return claims;
    }

    public Map<String, Object> getFeedbackClaims(Credential cred) {
        Map<String, Object> claims = new HashMap<>();
        ZonedDateTime iat = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime exp = iat.plusYears(1);
        claims.put("pmt", 3);
        claims.put("aud", cred.getIss());
        claims.put("iat", iat.toEpochSecond());
        claims.put("exp", exp.toEpochSecond());
        claims.put("serial_number", cred.getSerialNumber());
        return claims;
    }*/
}
