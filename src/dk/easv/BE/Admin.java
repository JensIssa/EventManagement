package dk.easv.BE;

import dk.easv.BE.enums.PersonType;

public class Admin extends Person {

    private String password;

    public Admin(int id, String name, String email,String password, PersonType type) {
        super(id, name, email, type);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
