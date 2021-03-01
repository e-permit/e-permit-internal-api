package epermit.data.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import epermit.common.CommandResult;
import epermit.core.aurthorities.AuthorityDto;
import epermit.core.aurthorities.AuthorityService;
import epermit.core.aurthorities.CreateAuthorityInput;
import epermit.core.aurthorities.CreateAuthorityKeyInput;
import epermit.core.aurthorities.CreateAuthorityQuotaInput;
import epermit.core.aurthorities.CreateIssuerQuotaInput;
import epermit.core.aurthorities.SetClaimsRuleInput;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityKey;
import epermit.data.entities.VerifierQuota;
import epermit.data.entities.IssuerQuota;
import epermit.data.repositories.AuthorityKeyRepository;
import epermit.data.repositories.AuthorityQuotaRepository;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.repositories.IssuerQuotaRepository;
import lombok.SneakyThrows;

@Component
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityKeyRepository authorityKeyRepository;
    private final AuthorityQuotaRepository authorityQuotaRepository;
    private final IssuerQuotaRepository issuerQuotaRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper,
            AuthorityKeyRepository authorityKeyRepository,
            AuthorityQuotaRepository authorityQuotaRepository,
            IssuerQuotaRepository issuerQuotaRepository){
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
        this.authorityKeyRepository = authorityKeyRepository;
        this.authorityQuotaRepository = authorityQuotaRepository;
        this.issuerQuotaRepository = issuerQuotaRepository;
    }

    @Override
    @SneakyThrows
    public List<AuthorityDto> getAll() {
        List<epermit.data.entities.Authority> all = authorityRepository.findAll();
        return all.stream().map(x -> modelMapper.map(x, AuthorityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public AuthorityDto getByCode(String code) {
        Authority authority = authorityRepository.findByCode(code).get();
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult create(CreateAuthorityInput input) {
        Authority authority = new Authority();
        authority.setCode(input.getCode());
        authority.setName(input.getName());
        authority.setUri(input.getUri());
        authority.setCreatedAt(OffsetDateTime.now());
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult createKey(String code, CreateAuthorityKeyInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        AuthorityKey key = new AuthorityKey();
        key.setAuthority(authority);
        key.setKid(input.getKid());
        key.setContent(input.getJwk());
        key.setCreatedAt(OffsetDateTime.now());
        authority.addKey(key);
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult revokeKey(int id) {
        AuthorityKey key = authorityKeyRepository.getOne(id);
        key.setDisabled(true);
        key.setDisabledAt(OffsetDateTime.now());
        authorityKeyRepository.save(key);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult setClaimsRule(String code, SetClaimsRuleInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        authority.setClaimsRule(input.getRule());
        authorityRepository.save(authority);
        return CommandResult.success();
    }


    @Override
    @Transactional
    @SneakyThrows
    public CommandResult createAuthorityQuota(String code, CreateAuthorityQuotaInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        VerifierQuota quota = modelMapper.map(input, VerifierQuota.class);
        authority.addAuthorityQuota(quota);
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult revokeAuthorityQuota(int id) {
        VerifierQuota quota = authorityQuotaRepository.getOne(id);
        authorityQuotaRepository.delete(quota);
        return CommandResult.success();
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult createIssuerQuota(String code, CreateIssuerQuotaInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        IssuerQuota quota = modelMapper.map(input, IssuerQuota.class);
        authority.addIssuerQuota(quota);
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @Transactional
    @SneakyThrows
    public CommandResult revokeIssuerQuota(int id) {
        IssuerQuota quota = issuerQuotaRepository.getOne(id);
        issuerQuotaRepository.delete(quota);
        return CommandResult.success();
    }
}
