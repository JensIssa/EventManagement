package dk.easv.BE;

import dk.easv.BE.enums.PersonType;

public class User {
    private int id;
    private String email;
    private PersonType personType;
    private int phoneNumber;

    public User(int id, String email, PersonType personType, int phoneNumber) {
        this.id = id;
        this.email = email;
        this.personType = personType;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
