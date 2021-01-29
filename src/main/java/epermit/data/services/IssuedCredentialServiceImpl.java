package epermit.data.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import epermit.core.issuedcredentials.*;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

@Component
public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final ModelMapper modelMapper;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository,
            ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @SneakyThrows
    public Page<IssuedCredential> getAll(Pageable pageable) {
        Page<epermit.data.entities.IssuedCredential> entities = repository.findAll(pageable);
        Page<IssuedCredential> dtoPage =
                entities.map(x -> modelMapper.map(x, IssuedCredential.class));
        return dtoPage;
    }

    @Override
    public IssuedCredential getById(long id) {
        return modelMapper.map(repository.findById(id), IssuedCredential.class);
    }

    @Override
    public IssuedCredential getByQrCode(String qrCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IssuedCredential getBySerialNumber(String serialNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateResult create(CreateInput input) {
        // check input if has error send error
        // generate a pid for cred
        // generate qr code
        // generate cred jws
        // send to aud
        CreateResult result = CreateResult.builder().issued_credential(null).code("code")
                .message("message").build();
        return result;
    }

    @Override
    public IssuedCredential revoke() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IssuedCredential setUsed() {
        // TODO Auto-generated method stub
        return null;
    }

}
