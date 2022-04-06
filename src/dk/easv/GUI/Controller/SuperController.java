package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public abstract class SuperController {



    /**
     * gets the Name connected to the Textfield
     * @param nameField The name-field associated with the person
     * @return
     */
    public String getName(TextField nameField) {
        if (!nameField.getText().isEmpty()) {
            return nameField.getText().trim();
        }
        else{
            errorMessage("Indtast et gyldigt navn");
        }
        return null;
    }

    public LocalDate getLocalDate(DatePicker datePicker){
        if (datePicker.getValue() != null){
            return datePicker.getValue();
        }else
            errorMessage("Vælg en dato for dit event.");
        return null;
    }

    /**
     * gets the Email connected to the Textfield
     * @param emailField the email-field associated with the person
     * @return
     */

    public String getEmail(TextField emailField) {
        if (!emailField.getText().isEmpty() && isValid(emailField.getText())) {
            return emailField.getText();
        }
        else{
            errorMessage("Indtast en gyldig E-mail");
        }
        return null;
    }

    /**
     * tjekker om en email opfylder krav som at have "@" og slutter med fx ".dk"
     * @param email den email string du vil tjekke
     * @return true hvis den givne string opfylder kravene. else false
     */
    private boolean isValid(String email)
    {
        String emailRegex =
                "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
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
            errorMessage("Indtast et password");
        }
        return null;
    }

    public String getTime(TextField textField){
        if (validateTime(textField)){
            return textField.getText();
        }
        else return null;
    }

    public int getPhoneNumber(TextField phoneNumber){
        int number = 0;
        if (!phoneNumber.getText().trim().isEmpty()){
            number = Integer.parseInt(phoneNumber.getText().trim());
        }
        return number;
    }

    /**
     * Validere om den indtastede tid er et reelt tidspunkt
     * @param textField feltet der skal tjekkes.
     *                  Den valide pattern er "xx:xx" hvor x er tal der ikke overstiger et 24-timers ur
     * @return true hvis tiden findes, else false
     */
    private boolean validateTime(TextField textField){
        if (!textField.getText().isEmpty() && textField.getText().length() == 5) {
            String[] splitString = textField.getText().split(":");
            int[] intArray = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1])};
            if (intArray[0] <= 24 && intArray[1] <= 59) {
                return true;
            }
        }
        errorMessage("Indtast en tid i mellem 00:00-23:59 ");
        return false;
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
    public void errorMessage(String errorTxt) {
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
    public void openNewSceneWithPerson(Person person,String fxmlPath, String Title) throws IOException, SQLServerException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root.load());
        Stage stage = new Stage();
        stage.setScene(scene);

        IController controller = root.getController();
        controller.setPersonInfo(person);

        stage.setTitle(Title);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void openNewSceneWithEventPerson(Event event, Person person, String fxmlPath, String Title) throws IOException, SQLServerException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        IEventController iEventController = root.getController();
        iEventController.setEventInfo(event);
        IController iController = root.getController();
        iController.setPersonInfo(person);
        stage.setTitle(Title);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * Giver en listener til et tekstfelt der sikre at
     * tekstfeltet kun kan modtage tal i formaten "xx:xx", hvor x er tal
     * @param textField
     */
    public void addTimeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-2]?[0-9]?([\\:][0-5]?[0-9]?)?")) {
                textField.setText(oldValue);
            }
        });
    }

    public void addPhoneNumberListener(TextField movieRatingTxt) {
        movieRatingTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}")) {
                movieRatingTxt.setText(oldValue);
            }
        });
    }

    /**
     * Giver en listener der stopper et tekstfelt fra overskride
     * en karakterbegrænsning på 100 tegn
     * @param textField
     */
    public void maxLenghtListener(TextField textField, int characterLimit){
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.length()>characterLimit){
                textField.setText(oldValue);
            }
        } );
    }
    public void maxLenghtListenerTxtArea(TextArea textArea){
        textArea.textProperty().addListener((observable, oldValue, newValue) ->{

            if (newValue != null){
                if (newValue.length()>100){
                    textArea.setText(oldValue);
                }
            }
        } );
    }

    public Optional<ButtonType> confirmationBox(String string){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,string,ButtonType.YES,ButtonType.NO);
        return alert.showAndWait();
    }

}
