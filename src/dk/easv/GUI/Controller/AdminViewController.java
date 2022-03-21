package dk.easv.GUI.Controller;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.GUI.Model.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private TableColumn eventmanagersPassword;
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

    public AdminViewController() throws IOException {
        personModel = new PersonModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventmanagersNames.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
        eventmanagersEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("Email"));
        eventmanagersPassword.setCellValueFactory(new PropertyValueFactory<Person, String>("Password"));

        try {
            eventmanagerTable.setItems(personModel.getObservablePersons());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the fxml scene where you can edit an eventmanager
     *
     * @param actionEvent
     */

    public void handleOpenEdit(ActionEvent actionEvent) throws IOException {
        /*
        EventManager selectedEventmanager = (EventManager) eventmanagerTable.getSelectionModel().getSelectedItem();
        if (selectedEventmanager != null) {
            FXMLLoader root = new FXMLLoader(getClass().getResource("/dk/easv/GUI/View2/EditEventManager.fxml"));
            Scene mainWindowScene = new Scene(root.load());

            Stage editEventmanagerStage = new Stage();
            editEventmanagerStage.setScene(mainWindowScene);
            EditEventmanagerController editEventmanagerController = root.getController();
            editEventmanagerController.();
            editEventmanagerStage.setResizable(true);
            editEventmanagerStage.showAndWait();
            eventmanagerTable.getItems().clear();
            eventmanagerTable.setItems(personModel.getObservablePersons());
        } else {
            error("Select an eventmanager and try again");
        }
         */
        // FIXME: 3/20/2022 
    }


    /**
     * Shows error message
     * @param text
     */

    private void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Opens the fxml scene where you can add a new eventManager from
     * @param actionEvent
     * @throws IOException
     */
    public void handleOpenAdd(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddEventManager.fxml",  true,"Add Eventmanager", true);
        eventmanagerTable.getItems().clear();
        eventmanagerTable.setItems(personModel.getObservablePersons());
    }

}

