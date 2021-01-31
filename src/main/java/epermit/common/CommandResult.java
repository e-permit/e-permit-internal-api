package epermit.common;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
// @SuperBuilder(toBuilder = true) use @SuperBuilder(toBuilder = true) for child classes
public class CommandResult {
    private final int statusCode;
    
    private final String errorCode;

    private final String errorMessage;
    
    @Singular private Map<String, String> props;

    //private T result; usage: CommandResult.<T>builder() 
}
