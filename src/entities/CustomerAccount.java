package entities;

public class CustomerAccount {
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    private String alias;

    public CustomerAccount(String alias, String email, String firstName, String lastName, String type, int discountID){
        this.alias = alias;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.discountID = discountID;
    }

    private String email;
    private String firstName;
    private String lastName;
    private String type;
    private int discountID;
}
