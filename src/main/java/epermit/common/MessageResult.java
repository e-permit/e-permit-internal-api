package epermit.common;

import lombok.Data;

@Data
public class MessageResult {
    private Boolean succeed;

    private Boolean handled;

    private String errorCode;

    public static MessageResult success() {
        MessageResult cr = new MessageResult();
        cr.setSucceed(true);
        return cr;
    }

    public static MessageResult fail(String errorCode) {
        MessageResult cr = new MessageResult();
        cr.setSucceed(false);
        return cr;
    }
}
