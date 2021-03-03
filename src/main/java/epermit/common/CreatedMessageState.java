package epermit.common;

public enum CreatedMessageState {
    NEW(1), LOCKED(2), HANDLED(3);

    private Integer code;

    private CreatedMessageState(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
