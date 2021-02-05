package epermit.core.credentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import epermit.common.CommandResult;

public interface CredentialService {
    Page<CredentialDto> getAll(Pageable pageable);
    CredentialDto getById(long id);
    CredentialDto getByQrCode(String qrCode);
    CredentialDto getBySerialNumber(String serialNumber);
    CommandResult create(); // from issuer
    CommandResult delete(long id); // from issuer
    CommandResult setUsed(long id);
}
