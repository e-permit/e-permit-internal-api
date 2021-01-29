package epermit.common;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CommandResult {
    private final String code;

    private final String message; 
}
