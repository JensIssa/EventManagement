package dk.easv.GUI.Controller;

import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddGuestController extends SuperController implements Initializable {
    public Label labelYoungerKids;
    @FXML
    private Label labelAdult;
    @FXML
    private Label labelOlderKids;
    @FXML
    private ScrollPane scrollPane;
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
        maxLenghtListener(emailTxtField,150);
        addPhoneNumberListener(phonenumberTxtField);
    }
/*
    private void drawScrollPane(){
        HBox hBox = new HBox(40);

        //voksne
    VBox adults = createVbox("Voksne", labelAdult);

        //Store børn
     VBox old = createVbox("Ældre børn", labelOlderKids);
        //Små børn
     VBox young = createVbox("Yngre børn", labelYoungerKids);

     hBox.getChildren().addAll(adults, old, young);
        scrollPane.setContent(hBox);
    }
    */

/*
    private VBox createVbox(String text,Label label )
    {
        Label header = new Label();
        header.setText(text);
        int counter = Integer.parseInt(label.getText());
        VBox vBox = new VBox(20);
        Font font = new Font("Lucida Sans",  15);
        if (counter > 0) {
            vBox.getChildren().add(header);
            for (int i = 0; i < counter; i++) {
                TextField txtAdult = new TextField();
                txtAdult.setFont(font);
                vBox.getChildren().add(txtAdult);
            }
        }
        return vBox;
    }
*/
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

    public void handleSubstractOlderKids(ActionEvent actionEvent) {
        {
            int count = Integer.parseInt(labelOlderKids.getText());
            if (count > 0){
                count--;
                labelOlderKids.setText(String.valueOf(count));
            }
        }
    }

    public void handleAddOlderKids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelOlderKids.getText());
        count++;
        labelOlderKids.setText(String.valueOf(count));

    }

    public void handleSubstractYoungerKids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelYoungerKids.getText());
        if (count > 0){
            count--;
            labelYoungerKids.setText(String.valueOf(count));
        }

    }

    public void handleAddYoungerkids(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelYoungerKids.getText());
        count++;
        labelYoungerKids.setText(String.valueOf(count));
    }

    public void handleAddAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        count++;
        labelAdult.setText(String.valueOf(count));

    }

    public void handleSubstractAdults(ActionEvent actionEvent) {
        int count = Integer.parseInt(labelAdult.getText());
        if (count > 0){
            count--;
            labelAdult.setText(String.valueOf(count));
        }
    }
}
