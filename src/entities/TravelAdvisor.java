package entities;

public class TravelAdvisor {

    private int code;
    private String firstName;
    private String lastName;

    public TravelAdvisor(int code, String firstName, String lastName) {
        this.firstName = firstName;
        this.code = code;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
