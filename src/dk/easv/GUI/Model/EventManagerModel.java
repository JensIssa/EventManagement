package dk.easv.GUI.Model;

import dk.easv.BE.PersonType;
import dk.easv.BLL.EventManagerManager;

import java.io.IOException;

public class EventManagerModel {
    private EventManagerManager eMM;
    public EventManagerModel() throws IOException {
        eMM = new EventManagerManager();
    }

    public PersonType getPersonType(String email, String password) {
        return eMM.getPersonType(email,password);
    }
}
