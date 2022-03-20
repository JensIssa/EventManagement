package dk.easv.GUI.Controller;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public void handleSaveBtn(ActionEvent actionEvent) {
        // FIXME: 3/20/2022 
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeWindow(cancelBtn);
    }

    public void setEventmanager(EventManager eventmanager) {
        // FIXME: 3/20/2022 
    }

    @Override
    void setPersonInfo(Person person) {
        // FIXME: 3/20/2022
    }
}
