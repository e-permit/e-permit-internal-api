package epermit.core.users;

import java.util.List;
import org.springframework.stereotype.Service;
import epermit.common.CommandResult;

@Service
public interface UserService {
    List<UserDto> getAll();

    UserDto getOne(String username);

    CommandResult Create(CreateUserInput input);
}
