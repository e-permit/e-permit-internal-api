package epermit.common;

import lombok.Getter;

@Getter
public class MessageHandleResult {
    private String resultCode;

    public static MessageHandleResult success(){
        MessageHandleResult r = new MessageHandleResult();
        r.resultCode = "SUCCESS";
        return r;
    }

    public static MessageHandleResult fail(String resultCode){
        MessageHandleResult r = new MessageHandleResult();
        r.resultCode = resultCode;
        return r;
    }
}
