package epermit.data.services;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import epermit.common.CommandResult;
import epermit.core.credentials.CredentialDto;
import epermit.core.credentials.CredentialService;
import epermit.data.entities.Permit;
import epermit.data.repositories.CredentialRepository;
import epermit.data.utils.CredentialUtils;
import epermit.data.utils.KeyUtils;
import epermit.data.utils.MessageUtils;
import lombok.SneakyThrows;

public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepository repository;
    private final ModelMapper modelMapper;
    private final MessageUtils messageUtils;
    private final CredentialUtils credentialUtils;
    private final KeyUtils keyUtils;

    public CredentialServiceImpl(CredentialRepository repository, ModelMapper modelMapper,
            CredentialUtils credentialUtils, MessageUtils messageUtils, KeyUtils keyUtils) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.messageUtils = messageUtils;
        this.credentialUtils = credentialUtils;
        this.keyUtils = keyUtils;
    }

    @Override
    @SneakyThrows
    public Page<CredentialDto> getAll(Pageable pageable) {
        Page<epermit.data.entities.Permit> entities = repository.findAll(pageable);
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
    @Transactional
    public CommandResult setUsed(long id) {
        Optional<Permit> credResult = repository.findById(id);
        if (!credResult.isPresent()) {
            return CommandResult.fail("CREDENTIAL_NOT_FOUND",
                    "Not found credential for id: " + Long.toString(id));
        }
        Permit cred = credResult.get();
        return CommandResult.success();
        /*String jws = keyUtils.createJws(credentialUtils.getFeedbackClaims(cred));
        boolean isSucceed = messageUtils.sendMesaage(cred.getIss(), jws);
        if (isSucceed) {
            cred.setUsed(true);
            repository.save(cred);
            return CommandResult.success();
        } else {
            return CommandResult.fail("MESSAGE_SEND_FAILURE",
                    "Couldn't send credential. Credential id: " + Long.toString(id));
        }*/
    }
}
