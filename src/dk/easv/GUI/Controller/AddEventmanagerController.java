package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddEventmanagerController extends SuperController {
    @FXML
    private Button saveBtn;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField eventTxtField;

    private PersonModel personModel;

    public AddEventmanagerController() throws IOException {
        personModel = new PersonModel();
    }

    /**
     * Closes the AddEventManager fxml
     * @param actionEvent
     */

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }

    /**
     * Adds a new eventmanager and closes the AddEventManager fxml
     * @param actionEvent
     */
    public void handleSaveBtn(ActionEvent actionEvent) {
        String name = getName(nameTxtField);
        String email = getEmail(emailTxtField);
        String password = getPassword(passwordTxtField);


        if (name != null && email != null && password != null) {
            personModel.createEventmanager(name, email, password, PersonType.EVENTMANAGER);
            closeWindow(saveBtn);
        }
    }
}
