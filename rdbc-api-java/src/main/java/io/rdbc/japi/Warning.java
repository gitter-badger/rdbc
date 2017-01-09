package io.rdbc.japi;

/*
 * Represents a warning emitted by a database engine during statement
 * processing.
 *
 * @param msg  database vendor specific warning message
 * @param code database vendor specific warning code
 */
//final case class Warning(msg: String, code: String)

public final class Warning {
    private String msg;
    private String code;

    Warning(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public static Warning of(String msg, String code) {
        return new Warning(msg, code);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
