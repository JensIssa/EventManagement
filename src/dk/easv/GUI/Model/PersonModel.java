package dk.easv.GUI.Model;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.BLL.PersonManager;

import java.io.IOException;

public class PersonModel {
    private PersonManager pM;
    public PersonModel() throws IOException {
        pM = new PersonManager();
    }


    public Person loginPerson(String email, String password){
        return pM.loginPerson(email,password);
    }
}
