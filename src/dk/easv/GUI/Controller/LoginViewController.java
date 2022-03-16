package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static dk.easv.BE.PersonType.ADMIN;
import static dk.easv.BE.PersonType.EVENTMANAGER;

public class LoginViewController {
    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField emailInput;

    PersonModel personModel;

    public LoginViewController() throws IOException {
        personModel = new PersonModel();
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        String email = emailInput.getText();
        String password = passwordInput.getText();

        //check valid login -> check type
        if (!email.isBlank() && !password.isBlank()){
            Person person = personModel.loginPerson(email,password);
            if (person!= null){
                PersonType type = person.getType();
                switch (type) {
                    case EVENTMANAGER -> openNewScene("/dk/easv/GUI/View/EventManagerView.fxml", "Title", actionEvent);
                    case ADMIN -> openNewScene("/dk/easv/GUI/View/AdminView.fxml", "Title", actionEvent);
                    case USER -> openNewScene("/dk/easv/GUI/View/UserView.fxml", "Title", actionEvent);
                }
            }else{
                System.out.println("person is null");
            }
        }else{
            //error
            System.out.println("error login");
        }
    }

    /**
     * åbner en nu scene i det nuværende vindue
     * @param fxmlPath path til den fxml fil der skal åbnes
     * @param Title
     * @param actionEvent
     * @throws IOException
     */
    private void openNewScene(String fxmlPath, String Title, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(Title);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
