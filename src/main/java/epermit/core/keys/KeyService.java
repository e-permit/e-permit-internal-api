package epermit.core.keys;

import java.util.List;

import epermit.common.CommandResult;

public interface KeyService {
    List<KeyDto> getKeys();
    CommandResult CreateKey(String kid);
    CommandResult EnableKey(long id); 
}
