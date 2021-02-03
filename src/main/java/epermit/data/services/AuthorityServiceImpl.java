package epermit.data.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
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
    private final TransactionTemplate transactionTemplate;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper,
            TransactionTemplate transactionTemplate, AuthorityKeyRepository authorityKeyRepository,
            AuthorityQuotaRepository authorityQuotaRepository) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
        this.transactionTemplate = transactionTemplate;
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
        Authority authority = authorityRepository.findByCode(code);
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    @SneakyThrows
    public CommandResult create(CreateAuthorityInput input) {
        transactionTemplate.executeWithoutResult(s -> {
            Authority authority = new Authority();
            authority.setCode(input.getCode());
            authority.setName(input.getName());
            authority.setUri(input.getUri());
            authority.setCreatedAt(new Date());
            authorityRepository.save(authority);
        });
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult createKey(String code, CreateAuthorityKeyInput input) {
        transactionTemplate.executeWithoutResult(s -> {
            Authority authority = authorityRepository.findByCode(code);
            AuthorityKey key = new AuthorityKey();
            key.setAuthority(authority);
            key.setKid(input.getKid());
            key.setContent(input.getJwk());
            key.setCreatedAt(new Date());
            authority.addKey(key);
            authorityRepository.save(authority);
        });

        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult createQuota(String code, CreateAuthorityQuotaInput input) {
        transactionTemplate.executeWithoutResult(s -> {
            Authority authority = authorityRepository.findByCode(code);
            AuthorityQuota quota = modelMapper.map(input, AuthorityQuota.class);
            authority.addQuota(quota);
            authorityRepository.save(authority);
        });
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult revokeKey(int id) {
        transactionTemplate.executeWithoutResult(s -> {
            AuthorityKey key = authorityKeyRepository.getOne(id);
            key.setDisabled(true);
            key.setDisabledAt(new Date());
            authorityKeyRepository.save(key);
        });
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult revokeQuota(int id) {
        transactionTemplate.executeWithoutResult(s -> {
            AuthorityQuota quota = authorityQuotaRepository.getOne(id);
            quota.setDisabled(true);
            quota.setDisabledAt(new Date());
            authorityQuotaRepository.save(quota);
        });
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult setClaimsRule(String code, SetClaimsRuleInput input) {
        transactionTemplate.executeWithoutResult(s -> {
            Authority authority = authorityRepository.findByCode(code);
            authority.setClaimsRule(input.getRule());
            authorityRepository.save(authority);
        });
        return CommandResult.success();
    }
}
