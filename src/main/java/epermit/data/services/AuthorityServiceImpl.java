package epermit.data.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import epermit.common.CommandResult;
import epermit.core.aurthorities.AuthorityDto;
import epermit.core.aurthorities.AuthorityService;
import epermit.core.aurthorities.CreateAuthorityInput;
import epermit.core.aurthorities.CreateAuthorityKeyInput;
import epermit.core.aurthorities.CreateAuthorityQuotaInput;
import epermit.core.aurthorities.SetClaimsRuleInput;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
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
        log.info("message");
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult create(CreateAuthorityInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult createKey(CreateAuthorityKeyInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult createQuota(CreateAuthorityQuotaInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult revokeKey(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult revokeQuota(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CommandResult setClaimsRule(SetClaimsRuleInput input) {
        // TODO Auto-generated method stub
        return null;
    }
}
