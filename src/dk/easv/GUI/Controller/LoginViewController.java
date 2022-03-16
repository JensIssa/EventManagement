package dk.easv.GUI.Controller;

import dk.easv.BE.PersonType;
import dk.easv.GUI.Model.EventManagerModel;
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

public class LoginViewController {
    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField emailInput;

    EventManagerModel eMModel;

    public LoginViewController() throws IOException {
        eMModel = new EventManagerModel();
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        String email = emailInput.getText();
        String password = passwordInput.getText();

        //check valid login -> check type
        if (!email.isBlank() && !password.isBlank()) {
            PersonType type = eMModel.getPersonType(email, password);
            if (type == null){
                System.out.println("user type is null");
                //error window
                //måske er login method redundandt? investigate
            }else
                switch (type) {
                    case EVENTMANAGER -> openNewScene("/dk/easv/GUI/View/EventManagerView.fxml", "Title", actionEvent);
                    case ADMIN -> openNewScene("/dk/easv/GUI/View/AdminView.fxml", "Title", actionEvent);
                    case USER -> openNewScene("/dk/easv/GUI/View/UserView.fxml", "Title", actionEvent);
                }
        }
    }

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
