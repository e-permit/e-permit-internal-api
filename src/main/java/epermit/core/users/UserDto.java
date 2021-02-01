package epermit.core.users;

import lombok.Data;

@Data
public class UserDto {
    private long id;

    private String username;

    private String password;

    private String role;

}
