package entities;

public class SysAccount {

    protected int code;
    protected String username;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected String type;

    public SysAccount(int code, String username, String type) {
        this.code = code;
        this.username = username;
        this.type = type;
    }


}
