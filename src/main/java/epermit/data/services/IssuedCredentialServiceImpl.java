package epermit.data.services;

import java.util.Date;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import epermit.common.CommandResult;
import epermit.core.issuedcredentials.*;
import epermit.data.entities.Authority;
import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuedCredentialRepository;
import lombok.SneakyThrows;

@Component
public class IssuedCredentialServiceImpl implements IssuedCredentialService {

    private final IssuedCredentialRepository repository;
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public IssuedCredentialServiceImpl(IssuedCredentialRepository repository,
            ModelMapper modelMapper, RestTemplate restTemplate,
            AuthorityRepository authorityRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
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
    public IssuedCredentialDto getBySerialNumber(String serialNumber) {
        return modelMapper.map(repository.findOneBySerialNumber(serialNumber),
                IssuedCredentialDto.class);
    }

    @Override
    @Transactional
    public CommandResult create(CreateIssuedCredentialInput input) {
        Authority authority = authorityRepository.findByCode(input.getAud());
        // Optional<IssuedCredential> lastCr = repository.findFirstByRevokedTrue();
        // authority.getQuotas().stream().filter(x-> x.getYear() == input.getPy() &&
        // x.getDirection() == 1);
        // find all credential
        // generate a pid for cred
        // generate qr code
        // generate cred jws
        // delete if revoked exists
        // send to verifier
        CommandResult result = CommandResult.success();
        return result;
    }

    @Override
    @Transactional
    public CommandResult send(long id) {
        IssuedCredential cred = repository.findById(id).get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(cred.getJws(), headers);
        Authority authority = authorityRepository.findByCode(cred.getAud());
        restTemplate.postForEntity(authority.getUri(), request, Boolean.class);
        cred.setUsed(true);
        repository.save(cred);
        return CommandResult.success();
    }

    @Override
    @Transactional
    public CommandResult revoke(long id) {
        IssuedCredential cred = repository.findById(id).get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("", headers);
        Authority authority = authorityRepository.findByCode(cred.getAud());
        restTemplate.postForEntity(authority.getUri(), request, Boolean.class);
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
