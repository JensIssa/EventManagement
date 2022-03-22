package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.User;

public class UserViewController extends SuperController implements IController {
    User user;

    @Override
    public void setPersonInfo(Person person) {
        user = (User) person;
    }
}
