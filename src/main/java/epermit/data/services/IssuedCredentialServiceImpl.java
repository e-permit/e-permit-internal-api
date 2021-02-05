package epermit.data.services;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import epermit.common.CommandResult;
import epermit.core.issuedcredentials.*;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityQuota;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

@Component
public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository,
            ModelMapper modelMapper,
            AuthorityRepository authorityRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.authorityRepository = authorityRepository;
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
        return modelMapper.map(repository.findById(id).get(), IssuedCredentialDto.class);
    }

    @Override
    public IssuedCredentialDto getByQrCode(String qrCode) {
        return modelMapper.map(repository.findOneByQrCode(qrCode).get(), IssuedCredentialDto.class);
    }

    @Override
    public IssuedCredentialDto getBySerialNumber(String serialNumber) {
        return modelMapper.map(repository.findOneBySerialNumber(serialNumber),
                IssuedCredentialDto.class);
    }

    @Override
    public CommandResult create(CreateIssuedCredentialInput input) {
        Authority authority = authorityRepository.findByCode(input.getAud());
        Optional<IssuedCredential> lastCr = repository.findFirstByRevokedTrue();
        authority.getQuotas().stream().filter(x-> x.getYear() == input.getPy() && x.getDirection() == 1);
        // find all credential
        // generate a pid for cred
        // generate qr code
        // generate cred jws
        CommandResult result = CommandResult.builder().resultCode("code").build();
        return result;
    }

    @Override
    public CommandResult send(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult revoke(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult setUsed(long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
