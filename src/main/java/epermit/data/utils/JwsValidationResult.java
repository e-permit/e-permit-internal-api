package epermit.data.utils;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class JwsValidationResult {

    private final Boolean isSucceed;

    private final String resultCode;

    private final String resultMessage;

    @Singular
    private Map<String, Object> claims;

    public static JwsValidationResult success(Map<String, Object> claims) {
        return JwsValidationResult.builder().isSucceed(true).claims(claims).build();
    }

    public static JwsValidationResult fail(String errorCode, String errorMessage) {
        return JwsValidationResult.builder().isSucceed(false).resultCode(errorCode)
                .resultMessage(errorMessage).build();
    }
}

