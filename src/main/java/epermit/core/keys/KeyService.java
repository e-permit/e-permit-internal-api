package epermit.core.keys;

import java.util.List;

import epermit.common.CommandResult;

public interface KeyService {
    List<KeyDto> getKeys();
    CommandResult createKey(String kid);
    CommandResult enableKey(int id); 
}
