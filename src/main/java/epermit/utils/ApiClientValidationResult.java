package epermit.utils;

import lombok.Data;

@Data
public class ApiClientValidationResult {
    private boolean isValid;

    private String errorCode;

    private String clientId;

    private String scope;

    private int expiration;

    public static ApiClientValidationResult error(String code){
        ApiClientValidationResult r = new ApiClientValidationResult();
        r.isValid = false;
        r.errorCode = code;
        return r;
    }

    public static ApiClientValidationResult success(String clientId, String scope, int expiration){
        ApiClientValidationResult r = new ApiClientValidationResult();
        r.isValid = true;
        r.clientId = clientId;
        r.scope= scope;
        r.expiration = expiration;
        return r;
    }
}
