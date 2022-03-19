package dk.easv.GUI.Controller;

import dk.easv.BE.Person;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class AdminViewController extends SuperController {
    private TableView eventmanagerTable;

    private PersonModel personModel;

    @Override
    void setPersonInfo(Person person) {

    }

    public void handleOpenEdit(ActionEvent actionEvent) {

    }

    public void handleOpenAdd(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddEventManager.fxml", false, true,"Add Eventmanager", true);
        eventmanagerTable.getItems().clear();
        eventmanagerTable.setItems(personModel.getObservableMovies());
    }

    public void openScene(String pathToFXML, boolean undecorated, boolean showAndWait, String title, boolean resizable) throws IOException {
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

