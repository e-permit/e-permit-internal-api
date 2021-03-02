package epermit.common;

import lombok.Data;

@Data
public class MessageResult {
    private Boolean succeed;

    private String ackProof;

    private String errorCode;

    private String errorMessage;

    public static MessageResult success(String ackProof) {
        MessageResult cr = new MessageResult();
        cr.setSucceed(true);
        return cr;
    }

    public static MessageResult fail(String errorCode, String errorMessage) {
        MessageResult cr = new MessageResult();
        cr.setSucceed(false);
        return cr;
    }
}
