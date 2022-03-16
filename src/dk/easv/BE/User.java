package dk.easv.BE;

import java.util.List;

public class User extends Person {

    private int phoneNumber;

    public User(int id, String name,String email, String password, PersonType type, int phoneNumber) {
        super(id, name ,email, password, type);
        phoneNumber = this.phoneNumber;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
