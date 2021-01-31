package epermit.core.issuedcredentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import epermit.common.CommandResult;

public interface IssuedCredentialService {
    Page<IssuedCredentialDto> getAll(Pageable pageable);
    IssuedCredentialDto getById(long id);
    IssuedCredentialDto getByQrCode(String qrCode);
    IssuedCredentialDto getBySerialNumber(String serialNumber);
    CommandResult create(CreateIssuedCredentialInput input);
    CommandResult send();
    CommandResult revoke();
    CommandResult setUsed();
}
