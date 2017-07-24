/**
 * Created by student on 2017. 4. 8..
 */
public class Address {
    // Constructors
    public Address() { }

    public Address(String name, String phoneNumber, String email, String address, String memo) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.memo = memo;
    }

    // getters
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getMemo() {
        return memo;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    // member function
    public Address clone(){
        return new Address(new String(name), new String(phoneNumber), new String(email), new String(address), new String(memo));
    }


    // member variables
    private String name = new String();
    private String phoneNumber = new String();
    private String email = new String();
    private String address = new String();
    private String memo = new String();
}
