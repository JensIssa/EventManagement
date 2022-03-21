package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class SuperController {



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

    /**
     * Metode anvendt til at åbne de forskellige vinduer i programmet
     * @param pathToFXML - Stien til FXML vinduet
     * @param undecorated - et boolean parameter, der bestemmer hverenten der skal være dekorationer i et FXML vindue (dekorationer = forstørrrelse knappen, minimer knappen og exit krydset)
     * @param showAndWait - et boolean parameter der bestemmer om der skal anvendes showAndWait() eller show() metoden
     * @param title - titlen på FXML vinduet
     * @param resizable - et boolean parameter der bestemmer om et vindue kan redigeres i størrelsen
     * @throws IOException
     */
    public void openScene(String pathToFXML, boolean showAndWait, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.setTitle(title);
        stage.setResizable(resizable);

        stage.setScene(scene);
        if(showAndWait){
            stage.showAndWait();
        }

        if(!showAndWait){
            stage.show();
        }
    }


}
