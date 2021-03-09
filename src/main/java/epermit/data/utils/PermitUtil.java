package epermit.data.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import epermit.commands.CreatePermitCommand;
import epermit.common.PermitType;
import epermit.config.EPermitProperties;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuedPermit;
import epermit.data.entities.IssuerQuota;
import epermit.data.entities.VerifierQuota;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedPermitRepository;

@Component
public class PermitUtil {

    private final AuthorityRepository authorityRepository;
    private final IssuedPermitRepository issuedCredentialRepository;
    private final JwsUtil jwsUtil;
    private EPermitProperties props;

    public PermitUtil(AuthorityRepository authorityRepository, JwsUtil jwsUtil,
            IssuedPermitRepository issuedCredentialRepository, EPermitProperties props) {
        this.authorityRepository = authorityRepository;
        this.issuedCredentialRepository = issuedCredentialRepository;
        this.props = props;
        this.jwsUtil = jwsUtil;
    }

    public Integer generatePermitId(String aud, int py, PermitType pt) {
        Optional<Authority> authority = authorityRepository.findByCode(aud);
        Optional<IssuedPermit> revokedCred = issuedCredentialRepository.findFirstByRevokedTrue();
        if (revokedCred.isPresent()) {
            int nextPid = revokedCred.get().getPermitId();
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

    public String createPermitQrCode(String aud, String sub, PermitType pt, int py, int pid,
            String cn) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", sub);
        claims.put("pt", pt.getCode());
        claims.put("py", py);
        claims.put("pid", pid);
        claims.put("cn", cn);
        claims.put("iss", props.getIssuer().getCode());
        claims.put("iat", OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond());
        return jwsUtil.createJws(claims);
    }

    public String getSerialNumber(String aud, PermitType pt, Integer py, int pid) {
        StringJoiner joiner = new StringJoiner("-");
        String serialNumber =
                joiner.add(props.getIssuer().getCode()).add(aud).add(Integer.toString(py))
                        .add(Integer.toString(pt.getCode())).add(Long.toString(pid)).toString();
        return serialNumber;
    }

    public Boolean validatePermitId(String iss, PermitType pt, Integer py, int pid) {
        Authority authority = authorityRepository.findByCode(iss).get();
        Optional<VerifierQuota> quotaResult = authority.getVerifierQuotas().stream()
                .filter(x -> x.getYear() == py && pid < x.getEndNumber() && pid > x.getStartNumber()
                        && x.getPermitType() == pt)
                .findAny();
        return quotaResult.isPresent();
    }

    public IssuedPermit getPermitFromCommand(CreatePermitCommand cmd, Integer pid) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Gson gson = new Gson();
        String serialNumber =
                getSerialNumber(cmd.getIssuedFor(), cmd.getPermitType(), cmd.getPermitYear(), pid);
        String qrCode = createPermitQrCode(cmd.getIssuedFor(), cmd.getPlateNumber(),
                cmd.getPermitType(), cmd.getPermitYear(), pid, cmd.getCompanyName());
        IssuedPermit permit = new IssuedPermit();
        permit.setIssuedFor(cmd.getIssuedFor());
        permit.setClaims(gson.toJson(cmd.getClaims()));
        permit.setQrCode(qrCode);
        permit.setCompanyName(cmd.getCompanyName());
        permit.setExpireAt(OffsetDateTime.now().plusYears(1).format(dtf));
        permit.setIssuedAt(OffsetDateTime.now().format(dtf));
        permit.setPermitId(pid);
        permit.setPermitType(cmd.getPermitType());
        permit.setPermitYear(cmd.getPermitYear());
        permit.setPlateNumber(cmd.getPlateNumber());
        permit.setSerialNumber(serialNumber);
        return permit;
    }

    /*
     * public Map<String, Object> getPermitClaims(CreateIssuedCredentialInput input, int pid) {
     * Map<String, Object> claims = new HashMap<>(); Instant iat = Instant.now(); Instant exp =
     * iat.plus(1, ChronoUnit.YEARS); claims.put("aud", input.getAud()); claims.put("pmt", 1);
     * claims.put("iat", iat); claims.put("exp", exp); claims.put("sub", input.getSub());
     * claims.put("pt", input.getPt()); claims.put("py", input.getPy()); claims.put("pid", pid);
     * claims.put("cn", input.getCn()); claims.put("serial_number", getSerialNumber(input, pid));
     * input.getClaims().forEach((k, v) -> { claims.put(k, v); }); return claims; }
     * 
     * public Map<String, Object> getRevokeClaims(IssuedCredential cred) { Map<String, Object>
     * claims = new HashMap<>(); Instant iat = Instant.now(); Instant exp = iat.plus(1,
     * ChronoUnit.YEARS); claims.put("pmt", 2); claims.put("aud", cred.getAud()); claims.put("iat",
     * iat); claims.put("exp", exp); claims.put("serial_number", cred.getSerialNumber()); return
     * claims; }
     * 
     * public Map<String, Object> getFeedbackClaims(Credential cred) { Map<String, Object> claims =
     * new HashMap<>(); ZonedDateTime iat = ZonedDateTime.now(ZoneOffset.UTC); ZonedDateTime exp =
     * iat.plusYears(1); claims.put("pmt", 3); claims.put("aud", cred.getIss()); claims.put("iat",
     * iat.toEpochSecond()); claims.put("exp", exp.toEpochSecond()); claims.put("serial_number",
     * cred.getSerialNumber()); return claims; }
     */
}
