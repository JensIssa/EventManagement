package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public abstract class SuperController {
    abstract void setPersonInfo(Person person);


    /**
     * gets the Name connected to the Textfield
     * @param nameField The name-field associated with the person
     * @return
     */

    public String getName(TextField nameField) {
        if (!nameField.getText().isEmpty()) {
            return nameField.getText();
        }
        else{
            errorMessage("Please provide a name for the person");
        }
        return null;
    }

    /**
     * gets the Email connected to the Textfield
     * @param emailField the email-field associated with the person
     * @return
     */

    public String getEmail(TextField emailField) {
        if (!emailField.getText().isEmpty()) {
            return emailField.getText();
        }
        else{
            errorMessage("Please provide an email associated with the person");
        }
        return null;
    }

    /**
     * gets the password connected to the TextField
     * @param passwordField
     * @return
     */

    public String getPassword(TextField passwordField) {
        if (!passwordField.getText().isEmpty()) {
            return passwordField.getText();
        }
        else{
            errorMessage("Please provide a password associated with the person");
        }
        return null;
    }

    /**
     * The scene gets closed
     * @param anyButton
     */
    public void closeWindow(Button anyButton){
        Stage stage = (Stage) anyButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows error message
     * @param errorTxt
     */
    private void errorMessage(String errorTxt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(errorTxt);
        alert.showAndWait();
    }

}
