package epermit.data.services;

import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.ECKey;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;

import epermit.core.keys.KeyService;
import epermit.data.entities.Key;
import epermit.data.repositories.KeyRepository;
import epermit.utils.KeyUtils;
import lombok.SneakyThrows;

@Component
public class KeyServiceImpl implements KeyService {

    private String password;

    private final KeyRepository keyRepository;

    public KeyServiceImpl(KeyRepository keyRepository, @Value("${epermit.key.password}") String password) {
        this.keyRepository = keyRepository;
        this.password = password;
    }

    @SneakyThrows
    public ECKey getCurrentKey() {
        Key keyRow = keyRepository.getEnabled();
        ECKey key = KeyUtils.GetKey(password, keyRow.getSalt(), keyRow.getContent());
        return key;
    }

    @Override
    @SneakyThrows
    public Pair<String, String> CreateKey(String kid) {
        Pair<String, String> keyInfo = KeyUtils.Create(kid, password);
        Key k = new Key();
        k.setKid(kid);
        k.setCreatedAt(new Date());
        k.setEnabled(false);
        k.setSalt(keyInfo.getFirst());
        k.setContent(keyInfo.getSecond());
        keyRepository.save(k);
        return keyInfo;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void EnableKey(String kid) {
        Key current = keyRepository.getEnabled();
        current.setEnabled(false);
        Key newOne = keyRepository.findByKid(kid);
        newOne.setEnabled(true);
        keyRepository.save(current);
        keyRepository.save(newOne);
    }
}
