package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    /**
     * åbner en nu scene i det nuværende vindue
     * @param fxmlPath path til den fxml fil der skal åbnes
     * @param Title
     * @throws IOException
     */
    public void openNewSceneWithPerson(Person person,String fxmlPath, String Title) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root.load());
        Stage stage = new Stage();
        stage.setScene(scene);

        IController controller = root.getController();
        controller.setPersonInfo(person);

        stage.setTitle(Title);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Giver en listener til et tekstfelt der sikre at
     * tekstfeltet kun kan modtage tal
     * @param textField
     */
    public void addNumbersOnlyListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-2]?[0-9]?([\\:][0-5]?[0-9]?)?")) {
                textField.setText(oldValue);
            }
        });
    }

    public void maxLenghtListener(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.length()>=101){
                textField.setText(oldValue);
            }
        } );
    }
}
