package epermit.core.credentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import epermit.common.CommandResult;

public interface CredentialService {
    Page<CredentialDto> getAll(Pageable pageable);
    CredentialDto getById(long id);
    CredentialDto getBySerialNumber(String serialNumber);
    CommandResult setUsed(long id);
    //CommandResult create(String jwt); // from issuer
    //CommandResult delete(String serialNumber, String jwt); // from issuer
}
