package epermit.core.issuedcredentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssuedCredentialService {
    Page<IssuedCredential> getAll(Pageable pageable);
    IssuedCredential getById();
    IssuedCredential getByQrCode();
    IssuedCredential getBySerialNumber();
    IssuedCredential create();
    IssuedCredential revoke();
    IssuedCredential setUsed();
}
