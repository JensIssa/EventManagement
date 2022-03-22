package dk.easv.GUI.Controller;

import dk.easv.BE.Admin;
import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BE.PersonType;
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
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminViewController extends SuperController implements Initializable, IController {
    @FXML
    private TableColumn<Person,String> eventmanagersNames;
    @FXML
    private TableColumn<Person, String> eventmanagersEmail;
    @FXML
    private TableView<Person> eventmanagerTable;

    private Admin admin;
    private PersonModel personModel;


    public AdminViewController() throws IOException {
        personModel = new PersonModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventmanagersNames.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
        eventmanagersEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("Email"));


        try {
            eventmanagerTable.setItems(personModel.getObservablePersons());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPersonInfo(Person person) {
        this.admin = (Admin) person;
        System.out.println(person); //temp. måske ikke nødtvendigt til admin siden det måske er ligemeget hvilken admin der er logget ind
    }

    /**
     * Opens the fxml scene where you can edit an eventmanager
     *
     * @param actionEvent
     */
    public void handleOpenEdit(ActionEvent actionEvent) throws IOException {
        EventManager selectedEventmanager = (EventManager) eventmanagerTable.getSelectionModel().getSelectedItem();
        if (selectedEventmanager != null) {
            FXMLLoader root = new FXMLLoader(getClass().getResource("/dk/easv/GUI/View2/EditEventManager.fxml"));
            Scene mainWindowScene = new Scene(root.load());

            Stage editEventmanagerStage = new Stage();
            editEventmanagerStage.setScene(mainWindowScene);
            EditEventmanagerController editEventmanagerController = root.getController();
            editEventmanagerController.setEventmanager(selectedEventmanager);
            editEventmanagerStage.setResizable(false);
            editEventmanagerStage.showAndWait();
            eventmanagerTable.getItems().clear();
            eventmanagerTable.setItems(personModel.getObservablePersons());
        } else {
            error("Select an eventmanager and try again");
        }
    }


    /**
     * Viser en error besked
     * @param text den error tekst der skal vises til brugeren
     */
    private void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Håndterer remove knappen
     * @param actionEvent
     */
    public void handleRemoveButton(ActionEvent actionEvent) {
        if (eventmanagerTable.getSelectionModel().getSelectedItem() == null) {
            error("Vælg hvilken person du vil slette");
        } else {
            EventManager eventManager = (EventManager) eventmanagerTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Er du sikker på at du vil slette " + eventManager.getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                personModel.deleteEventmanager(eventManager, PersonType.EVENTMANAGER);
                eventmanagerTable.getItems().remove(eventManager);
            }
        }
    }

    public void handleAddManagerButton(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddEventManager.fxml",  true,"Add Eventmanager", true);
        eventmanagerTable.getItems().clear();
        eventmanagerTable.setItems(personModel.getObservablePersons());
    }

    public void handleEditButton(ActionEvent actionEvent) {
    }
}

