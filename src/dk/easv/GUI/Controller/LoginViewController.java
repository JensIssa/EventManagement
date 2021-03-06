package dk.easv.GUI.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController extends SuperController{
    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField emailInput;

    PersonModel personModel;

    public LoginViewController() throws IOException {
        personModel = new PersonModel();
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException, SQLServerException {
        String email = emailInput.getText();
        String password = passwordInput.getText();

        //check valid login -> check type
        if (!email.isBlank() && !password.isBlank()){
            Person person = personModel.loginPerson(email,password);
            if (person!= null){
                PersonType type = person.getType();
                switch (type) {
                    case EVENTMANAGER -> openNewScene(person, "/dk/easv/GUI/View2/EventManagerView.fxml", "EventManager", actionEvent);
                    case ADMIN -> openNewScene(person,"/dk/easv/GUI/View2/AdminView2.fxml", "Admin", actionEvent);
                }
            }else{
                errorMessage("Ugyldigt Login");
            }
        }else{
            //error
            errorMessage("Ugyldigt Login");
        }
    }

    /**
     * åbner en nu scene i det nuværende vindue
     * @param fxmlPath path til den fxml fil der skal åbnes
     * @param Title
     * @param actionEvent
     * @throws IOException
     */
    private void openNewScene(Person person,String fxmlPath, String Title, ActionEvent actionEvent) throws IOException, SQLServerException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);

        IController controller = root.getController();
        controller.setPersonInfo(person);

        stage.setTitle(Title);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
