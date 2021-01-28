package epermit.data.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import epermit.core.aurthorities.Authority;
import epermit.core.aurthorities.AuthorityService;
import epermit.core.aurthorities.CreateInput;
import epermit.core.aurthorities.CreateKeyInput;
import epermit.core.aurthorities.CreateKeyResult;
import epermit.core.aurthorities.CreateQuotaInput;
import epermit.core.aurthorities.CreateQuotaResult;
import epermit.core.aurthorities.CreateResult;
import epermit.core.aurthorities.RevokeKeyInput;
import epermit.core.aurthorities.RevokeKeyResult;
import epermit.core.aurthorities.RevokeQuotaInput;
import epermit.core.aurthorities.RevokeQuotaResult;
import epermit.core.aurthorities.SetClaimsRuleInput;
import epermit.core.aurthorities.SetClaimsRuleResult;
import epermit.data.repositories.AuthorityRepository;
import lombok.SneakyThrows;

@Component
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @SneakyThrows
    public List<Authority> getAll() {
        List<epermit.data.entities.Authority> all = authorityRepository.findAll();
        return all.stream().map(x -> modelMapper.map(x, Authority.class)).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Authority getByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CreateResult create(CreateInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CreateKeyResult createKey(CreateKeyInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public CreateQuotaResult createQuota(CreateQuotaInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public RevokeKeyResult revokeKey(RevokeKeyInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public RevokeQuotaResult revokeQuota(RevokeQuotaInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SneakyThrows
    public SetClaimsRuleResult setClaimsRule(SetClaimsRuleInput input) {
        // TODO Auto-generated method stub
        return null;
    }

}
