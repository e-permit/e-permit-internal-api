package epermit.data.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import epermit.common.CommandResult;
import epermit.core.keys.*;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.data.utils.KeyUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeyServiceImpl implements KeyService {

    private final ModelMapper modelMapper;
    private final KeyRepository repository;
    private final KeyUtils keyUtils;

    public KeyServiceImpl(KeyRepository repository, ModelMapper modelMapper, KeyUtils keyUtils) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.keyUtils = keyUtils;
    }

    @Override
    @SneakyThrows
    public List<KeyDto> getKeys() {
        return repository.findAll().stream().map(x -> modelMapper.map(x, KeyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult createKey(String kid) {
        Key k = keyUtils.Create(kid);
        repository.save(k);
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult enableKey(int id) {
        Key oldKey = repository.findOneByEnabledTrue().get();
        oldKey.setEnabled(false);
        Key newOne = repository.findById(id).get();
        newOne.setEnabled(true);
        repository.save(oldKey);
        repository.save(newOne);
        return CommandResult.success();
    }

}
