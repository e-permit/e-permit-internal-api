package epermit.data.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import epermit.common.CommandResult;
import epermit.core.keys.*;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.utils.KeyUtils;
import lombok.SneakyThrows;

@Component
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
    public CommandResult CreateKey(String kid) {
        Pair<String, String> keyInfo = keyUtils.Create(kid);
        Key k = new Key();
        k.setKid(kid);
        k.setCreatedAt(new Date());
        k.setEnabled(false);
        k.setSalt(keyInfo.getFirst());
        k.setContent(keyInfo.getSecond());
        repository.save(k);
        return CommandResult.builder().build();
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResult EnableKey(long id) {
        Key oldKey = repository.getEnabled();
        oldKey.setEnabled(false);
        Key newOne = repository.findById(id).get();
        newOne.setEnabled(true);
        repository.save(oldKey);
        repository.save(newOne);

        return CommandResult.builder().build();
    }

}
