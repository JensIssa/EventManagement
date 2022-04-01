package dk.easv.GUI.Controller;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditEventmanagerController extends SuperController implements IController {
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField passwordTxtfield;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField nameTxtField;

    private PersonModel personModel;

    EventManager eventManager=null;

    public EditEventmanagerController() throws IOException {
        personModel = new PersonModel();
    }

    @Override
    public void setPersonInfo(Person person) {
        this.eventManager = (EventManager) person;
        nameTxtField.setText(eventManager.getName());
        emailTxtField.setText(eventManager.getEmail());
        passwordTxtfield.setText(eventManager.getPassword());
    }

    /**
     * Håndtere save knappens funktion til at læse tekstfelternes indhold og opdatere databasen
     * @param actionEvent
     */
    public void handleSaveBtn(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        String email = getEmail(emailTxtField);
        String password = getPassword(passwordTxtfield);
        if (name != null && email != null && password != null)
        {
            int id = eventManager.getId();
            EventManager eventManager = new EventManager(id, name, email, password, PersonType.EVENTMANAGER);
            personModel.updateEventmanager(eventManager);
            closeWindow(saveBtn);
        }
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }


}
