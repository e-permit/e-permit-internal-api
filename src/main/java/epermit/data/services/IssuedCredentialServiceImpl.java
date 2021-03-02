package epermit.data.services;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import epermit.common.CommandResult;
import epermit.core.issuedcredentials.*;
import epermit.core.messages.MessageType;
import epermit.core.messages.types.CreatePermitMessage;
import epermit.data.entities.IssuedPermit;
import epermit.data.repositories.IssuedCredentialRepository;
import epermit.data.utils.CredentialUtils;
import epermit.data.utils.KeyUtils;
import epermit.data.utils.MessageUtils;
import lombok.SneakyThrows;

@Component
public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final CredentialUtils credentialUtils;
    private final KeyUtils keyUtils;
    private final ModelMapper modelMapper;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository,
            ModelMapper modelMapper, MessageUtils messageUtils, CredentialUtils credentialUtils,
            KeyUtils keyUtils) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.credentialUtils = credentialUtils;
        this.keyUtils = keyUtils;
    }

    @Override
    @SneakyThrows
    public Page<IssuedCredentialDto> getAll(Pageable pageable) {
        Page<epermit.data.entities.IssuedPermit> entities = repository.findAll(pageable);
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
        int pid = credentialUtils.getPermitId(input.getIssuedFor(), input.getPermitYear(), input.getPermitType());
        Map<String, Object> qrCodeClaims = credentialUtils.getPermitQrCodeClaims(input, pid);
        // Map<String, Object> claims = credentialUtils.getPermitClaims(input, pid);
        String qrCode = keyUtils.createJwt(input.getIssuedFor(), qrCodeClaims);
        IssuedPermit cred = new IssuedPermit();
        cred.setAud(input.getIssuedFor());
        cred.setQrcode(qrCode);
        repository.save(cred);
        CreatePermitMessage message = CreatePermitMessage.builder()
                .companyName(input.getCompanyName()).permitId(pid).messageType(MessageType.CREATE_PERMIT.name())
                .permitType(input.getPermitType().getStringCode()).permitYear(input.getPermitYear()).serialNumber("")
                .claims(input.getClaims()).build();
        CommandResult result = CommandResult.success();
        return result;
    }

    /*
     * @Override
     * 
     * @Transactional public CommandResult send(long id) { IssuedCredential cred =
     * repository.findById(id).get(); // create event
     * 
     * boolean isSucceed = messageUtils.sendMesaage(cred.getAud(), cred.getJws()); if (isSucceed) {
     * cred.setUsed(true); repository.save(cred); return CommandResult.success(); } else { return
     * CommandResult.fail("MESSAGE_SEND_FAILURE", "Couldn't send credential. Credential id: " +
     * Long.toString(id)); } }
     */

    @Override
    @Transactional
    public CommandResult revoke(long id) {
        IssuedPermit cred = repository.findById(id).get();
        // create event
        cred.setRevoked(true);
        cred.setRevokedAt(OffsetDateTime.now());
        repository.save(cred);
        return CommandResult.success();

        // String jws = keyUtils.createJws(credentialUtils.getRevokeClaims(cred));
        /*
         * boolean isSucceed = messageUtils.sendMesaage(cred.getAud(), jws); if (isSucceed) {
         * cred.setRevoked(true); cred.setRevokedAt(OffsetDateTime.now()); repository.save(cred);
         * return CommandResult.success(); } else { return
         * CommandResult.fail("MESSAGE_SEND_FAILURE", "Couldn't send credential. Credential id: " +
         * Long.toString(id)); }
         */
    }

}
