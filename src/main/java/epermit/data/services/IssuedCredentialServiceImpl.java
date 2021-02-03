package epermit.data.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import epermit.common.CommandResult;
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
    public Page<IssuedCredentialDto> getAll(Pageable pageable) {
        Page<epermit.data.entities.IssuedCredential> entities = repository.findAll(pageable);
        Page<IssuedCredentialDto> dtoPage =
                entities.map(x -> modelMapper.map(x, IssuedCredentialDto.class));
        return dtoPage;
    }

    @Override
    public IssuedCredentialDto getById(long id) {
        return modelMapper.map(repository.findById(id), IssuedCredentialDto.class);
    }

    @Override
    public IssuedCredentialDto getByQrCode(String qrCode) {
        return modelMapper.map(repository.findByQrCode(qrCode), IssuedCredentialDto.class);
    }

    @Override
    public IssuedCredentialDto getBySerialNumber(String serialNumber) {
        return modelMapper.map(repository.findBySerialNumber(serialNumber),
                IssuedCredentialDto.class);
    }

    @Override
    public CommandResult create(CreateIssuedCredentialInput input) {
        // check input if has error send error
        // generate a pid for cred
        // generate qr code
        // generate cred jws
        CommandResult result = CommandResult.builder().resultCode("code").build();
        return result;
    }

    @Override
    public CommandResult send() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult revoke() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult setUsed() {
        // TODO Auto-generated method stub
        return null;
    }

}
