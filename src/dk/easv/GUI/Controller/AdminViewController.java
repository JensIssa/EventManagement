package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminViewController extends SuperController implements Initializable {
    @FXML
    private TableColumn eventmanagersNames;
    @FXML
    private TableColumn eventmanagersEmail;
    @FXML
    private TableColumn eventsAttended;
    @FXML
    private TableView eventmanagerTable;

    private PersonModel personModel;

    @Override
    void setPersonInfo(Person person) {
        // FIXME: 3/19/2022
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventmanagersNames.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
        eventmanagersEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("E-mail"));

        try {
            eventmanagerTable.setItems(personModel.getObservablePersons());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleOpenEdit(ActionEvent actionEvent) {
        // FIXME: 3/19/2022
    }

    public void handleOpenAdd(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddEventManager.fxml", false, true,"Add Eventmanager", true);
        eventmanagerTable.getItems().clear();
        eventmanagerTable.setItems(personModel.getObservablePersons());
    }

    private void openScene(String pathToFXML, boolean undecorated, boolean showAndWait, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        if(undecorated){
            stage.initStyle(StageStyle.UNDECORATED);
        }
        if(!undecorated){
            stage.initStyle(StageStyle.DECORATED);
        }

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

