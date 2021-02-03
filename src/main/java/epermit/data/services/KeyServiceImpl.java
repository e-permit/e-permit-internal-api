package epermit.data.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
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
    private final TransactionTemplate transactionTemplate;

    public KeyServiceImpl(KeyRepository repository, ModelMapper modelMapper, KeyUtils keyUtils,
            TransactionTemplate transactionTemplate) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.keyUtils = keyUtils;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    @SneakyThrows
    public List<KeyDto> getKeys() {
        return repository.findAll().stream().map(x -> modelMapper.map(x, KeyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public CommandResult CreateKey(String kid) {
        Key k = keyUtils.Create(kid);
        int id = transactionTemplate.execute(status -> {
            repository.save(k);
            return k.getId();
        });
        
        return CommandResult.success();
    }

    @Override
    @SneakyThrows
    public CommandResult EnableKey(int id) {
        transactionTemplate.executeWithoutResult(cx -> {
            Key oldKey = repository.getEnabled();
            oldKey.setEnabled(false);
            Key newOne = repository.findById(id).get();
            newOne.setEnabled(true);
            repository.save(oldKey);
            repository.save(newOne);
        });
        return CommandResult.success();
    }

}
