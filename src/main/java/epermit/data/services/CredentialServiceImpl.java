package epermit.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import epermit.common.CommandResult;
import epermit.core.credentials.CredentialDto;
import epermit.core.credentials.CredentialService;

public class CredentialServiceImpl implements CredentialService {
    // @Override
    public CommandResult create() {
        // TODO Auto-generated method stub
        return CommandResult.builder().build();
    }

    @Override
    public Page<CredentialDto> getAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CredentialDto getById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CredentialDto getByQrCode(String qrCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CredentialDto getBySerialNumber(String serialNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult delete(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult setUsed(long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
