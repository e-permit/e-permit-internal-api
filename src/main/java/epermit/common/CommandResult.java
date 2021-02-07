package epermit.common;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
// @SuperBuilder(toBuilder = true) use @SuperBuilder(toBuilder = true) for child classes
public class CommandResult {

    private final Boolean isSucceed;

    private final String resultCode;

    private final String resultMessage;

    @Singular
    private Map<String, String> props;

    public static CommandResult success() {
        return CommandResult.builder().isSucceed(true).build();
    }

    public static CommandResult fail(String errorCode, String errorMessage) {
        return CommandResult.builder().isSucceed(false).resultCode(errorCode)
                .resultMessage(errorMessage).build();
    }

    // private T result; usage: CommandResult.<T>builder()
}
