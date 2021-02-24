package epermit.data.services;

import java.util.Date;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import epermit.common.CommandResult;
import epermit.core.issuedcredentials.*;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.IssuedCredentialRepository;
import epermit.data.utils.CredentialUtils;
import epermit.data.utils.KeyUtils;
import lombok.SneakyThrows;

@Component
public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final CredentialUtils credentialUtils;
    private final KeyUtils keyUtils;
    private final ModelMapper modelMapper;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository,
            ModelMapper modelMapper, CredentialUtils credentialUtils, KeyUtils keyUtils) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.credentialUtils = credentialUtils;
        this.keyUtils = keyUtils;
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
    public IssuedCredentialDto getBySerialNumber(String serialNumber) {
        return modelMapper.map(repository.findOneBySerialNumber(serialNumber),
                IssuedCredentialDto.class);
    }

    @Override
    @Transactional
    public CommandResult create(CreateIssuedCredentialInput input) {
        int pid = credentialUtils.getPermitId(input.getAud(), input.getPy(), input.getPt());
        Map<String, Object> qrCodeClaims  = credentialUtils.getPermitQrCodeClaims(input, pid);
        Map<String, Object> claims = credentialUtils.getPermitClaims(input, pid);
        String jws = keyUtils.createJws(claims);
        String qrCode = keyUtils.createJws(qrCodeClaims);
        IssuedCredential cred = new IssuedCredential();
        cred.setAud(input.getAud());
        cred.setJws(qrCode);
        cred.setJws(jws);
        repository.save(cred);
        CommandResult result = CommandResult.success();
        return result;
    }

    @Override
    @Transactional
    public CommandResult send(long id) {
        IssuedCredential cred = repository.findById(id).get();
        //credentialUtils.sendMesaage(cred.getAud(), cred.getJws());
        cred.setUsed(true);
        repository.save(cred);
        return CommandResult.success();
    }

    @Override
    @Transactional
    public CommandResult revoke(long id) {
        IssuedCredential cred = repository.findById(id).get();
        
        cred.setRevoked(true);
        cred.setRevokedAt(new Date());
        repository.save(cred);
        return CommandResult.success();
    }

    /*
     * @Override public CommandResult setUsed(long id, String jwt) { // validate jwt // set used to
     * true return CommandResult.success(); }
     */

}
