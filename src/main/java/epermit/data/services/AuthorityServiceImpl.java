package epermit.data.services;

import java.util.Date;
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
import epermit.core.aurthorities.SetClaimsRuleInput;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityKey;
import epermit.data.entities.AuthorityQuota;
import epermit.data.repositories.AuthorityKeyRepository;
import epermit.data.repositories.AuthorityQuotaRepository;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

@Component
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityKeyRepository authorityKeyRepository;
    private final AuthorityQuotaRepository authorityQuotaRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper,
            AuthorityKeyRepository authorityKeyRepository,
            AuthorityQuotaRepository authorityQuotaRepository) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
        this.authorityKeyRepository = authorityKeyRepository;
        this.authorityQuotaRepository = authorityQuotaRepository;
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
    @SneakyThrows
    public CommandResult create(CreateAuthorityInput input) {
        Authority authority = new Authority();
        authority.setCode(input.getCode());
        authority.setName(input.getName());
        authority.setUri(input.getUri());
        authority.setCreatedAt(new Date());
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult createKey(String code, CreateAuthorityKeyInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        AuthorityKey key = new AuthorityKey();
        key.setAuthority(authority);
        key.setKid(input.getKid());
        key.setContent(input.getJwk());
        key.setCreatedAt(new Date());
        authority.addKey(key);
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult createQuota(String code, CreateAuthorityQuotaInput input) {
        Authority authority = authorityRepository.findByCode(code).get();
        AuthorityQuota quota = modelMapper.map(input, AuthorityQuota.class);
        authority.addQuota(quota);
        authorityRepository.save(authority);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult revokeKey(int id) {
        AuthorityKey key = authorityKeyRepository.getOne(id);
        key.setDisabled(true);
        key.setDisabledAt(new Date());
        authorityKeyRepository.save(key);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult revokeQuota(int id) {
        AuthorityQuota quota = authorityQuotaRepository.getOne(id);
        authorityQuotaRepository.delete(quota);
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
}
