package dk.easv.BE;

import dk.easv.BE.enums.PersonType;

public class User extends Person {

    private int phoneNumber;

    public User(int id, String name, String email, PersonType type, int phoneNumber) {
        super(id, name ,email, type);
        this.phoneNumber = phoneNumber;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
