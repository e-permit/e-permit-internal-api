package epermit.data.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import epermit.core.aurthorities.*;
import epermit.data.repositories.AuthorityRepository;

@Component
public class AuthorityServiceImpl implements AuthorityService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Authority> getAll() {
        List<epermit.data.entities.Authority> all = authorityRepository.findAll();
        return all.stream().map(x -> modelMapper.map(x, Authority.class)).collect(Collectors.toList());
    }

}
