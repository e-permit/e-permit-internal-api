package epermit.data.services;

import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import epermit.core.issuedcredentials.IssuedCredential;
import epermit.core.issuedcredentials.IssuedCredentialService;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final ModelMapper modelMapper;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @SneakyThrows
    public Page<IssuedCredential> getAll(Pageable pageable) {
        Page<epermit.data.entities.IssuedCredential> entities = repository.findAll(pageable);
        Page<IssuedCredential> dtoPage = entities.map(x-> modelMapper.map(x, IssuedCredential.class));
        return dtoPage;
    }

    @Override
    public IssuedCredential getById() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IssuedCredential getByQrCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IssuedCredential getBySerialNumber() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IssuedCredential create() {
        // TODO Auto-generated method stub
        return null;
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
