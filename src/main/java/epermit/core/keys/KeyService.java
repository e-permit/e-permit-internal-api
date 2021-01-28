package epermit.core.keys;

import com.nimbusds.jose.jwk.ECKey;

import org.springframework.data.util.Pair;

public interface KeyService {
    ECKey getCurrentKey();
    Pair<String, String> CreateKey(String kid);
    void EnableKey(String kid); 
}
