package epermit.core.issuedcredentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssuedCredentialService {
    Page<IssuedCredential> getAll(Pageable pageable);
    IssuedCredential getById(long id);
    IssuedCredential getByQrCode(String qrCode);
    IssuedCredential getBySerialNumber(String serialNumber);
    CreateResult create(CreateInput input);
    IssuedCredential revoke();
    IssuedCredential setUsed();
}
