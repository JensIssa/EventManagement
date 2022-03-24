package dk.easv.GUI.Controller;

import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddGuestController extends SuperController implements Initializable {
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField phonenumberTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    private PersonModel personModel;

    public AddGuestController() throws IOException {
        personModel = new PersonModel();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void handleCancelButton() {
        closeWindow(cancelBtn);
    }


    public void handleSaveUser(ActionEvent actionEvent) {
        String userName = getName(nameTxtField);
        String userEmail = getEmail(emailTxtField);
        int userPhoneNumber = getPhoneNumber(phonenumberTxtField);
        String userPassword = getPassword(passwordTxtField);

        if (userName != null && userEmail != null &&userPassword != null) {
            personModel.createuser(userName, userEmail, userPassword, userPhoneNumber);
            closeWindow(saveBtn);
        }
    }
}
