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

public class EditEventmanagerController extends SuperController {
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
    @FXML
    private TextField eventTxtField;

    private PersonModel personModel;

    private int id;

    public EditEventmanagerController() throws IOException {
        personModel = new PersonModel();
    }

    public void handleSaveBtn(ActionEvent actionEvent) {


        String name = getName(nameTxtField);
        String email = getEmail(emailTxtField);
        String password = getPassword(passwordTxtfield);


        if (name != null && email != null && password != null)
        {
            EventManager eventManager = new EventManager(id, name, email, password, PersonType.EVENTMANAGER);
            System.out.println(eventManager);
            personModel.updateEventmanager(eventManager);
            closeWindow(saveBtn);
        }
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }

    public void setEventmanager(EventManager eventmanager) {
        id = eventmanager.getId();
        nameTxtField.setText(eventmanager.getName());
        emailTxtField.setText(eventmanager.getEmail());
        passwordTxtfield.setText(eventmanager.getPassword());
    }
}
