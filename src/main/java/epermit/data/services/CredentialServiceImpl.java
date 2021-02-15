package epermit.data.services;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import epermit.common.CommandResult;
import epermit.core.credentials.CredentialDto;
import epermit.core.credentials.CredentialService;
import epermit.data.entities.Credential;
import epermit.data.repositories.CredentialRepository;
import lombok.SneakyThrows;

public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepository repository;
    private final ModelMapper modelMapper;
    private final TransactionTemplate transactionTemplate;

    public CredentialServiceImpl(CredentialRepository repository, ModelMapper modelMapper,
            TransactionTemplate transactionTemplate) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    @SneakyThrows
    public Page<CredentialDto> getAll(Pageable pageable) {
        Page<epermit.data.entities.Credential> entities = repository.findAll(pageable);
        Page<CredentialDto> dtoPage = entities.map(x -> modelMapper.map(x, CredentialDto.class));
        return dtoPage;
    }

    @Override
    public CredentialDto getById(long id) {
        return modelMapper.map(repository.findById(id).get(), CredentialDto.class);
    }

    @Override
    public CredentialDto getBySerialNumber(String serialNumber) {
        return modelMapper.map(repository.findOneBySerialNumber(serialNumber), CredentialDto.class);
    }

    @Override
    public CommandResult sendFeedback(long id) {
        Optional<Credential> credResult = repository.findById(id);
        if (!credResult.isPresent()) {
            return CommandResult.fail("CREDENTIAL_NOT_FOUND",
                    "Not found credential for id: " + Long.toString(id));
        }
        transactionTemplate.executeWithoutResult(action->{
            Credential cred = credResult.get();
            cred.setUsed(true);
            repository.save(cred);
        });
        return CommandResult.success();
    }

    /*@Override
    public CommandResult create(String jwt) {
        // validate jwt
        // validate info
        // create
        return CommandResult.success();
    }*/

    /*@Override
    public CommandResult delete(String serialNumber, String jwt) {
        // validate jwt and info
        Optional<Credential> credResult = repository.findOneBySerialNumber(serialNumber);
        if (!credResult.isPresent()) {
            return CommandResult.fail("CREDENTIAL_NOT_FOUND",
                    "Not found credential for serial number: " + serialNumber);
        }
        transactionTemplate.executeWithoutResult(action->{
            Credential cred = credResult.get();
            repository.delete(cred);
        });
        return CommandResult.success();
    }*/
}
