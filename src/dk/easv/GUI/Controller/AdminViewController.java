package dk.easv.GUI.Controller;

import dk.easv.BE.*;
import dk.easv.GUI.Model.EventModel;
import dk.easv.GUI.Model.PersonModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class AdminViewController extends SuperController implements Initializable, IController {
    @FXML
    private TextField searchTxt;
    @FXML
    private Button closeBtn;
    @FXML
    private TableView<Event> eventInformationTable;
    @FXML
    private TableColumn<Event,String> eventColumn;
    @FXML
    private TableColumn<Event, String> eventmanagerColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateStartColumn;
    @FXML
    private TableColumn<Event, String> startTimeColumn;
    @FXML
    private TableColumn<Person, String> eventmanagersNames;
    @FXML
    private TableColumn<Person, String> eventmanagersEmail;
    @FXML
    private TableView<Person> eventmanagerTable;

    private Admin admin;
    private PersonModel personModel;
    private EventModel eventModel;


    public AdminViewController() throws IOException, SQLException {
        personModel = new PersonModel();
        eventModel = new EventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventmanagersNames.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
        eventmanagersEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("Email"));

        eventmanagerColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("managerName"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("Name"));
        dateStartColumn.setCellValueFactory(new PropertyValueFactory<Event, LocalDate>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("startTime"));


        try {
            eventmanagerTable.setItems(personModel.getObservablePersons());
            eventInformationTable.setItems(eventModel.getObservableEvents());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setPersonInfo(Person person) {
        this.admin = (Admin) person;
        System.out.println(person); //temp. måske ikke nødtvendigt til admin siden det måske er ligemeget hvilken admin der er logget ind
    }


    /**
     * Viser en error besked
     *
     * @param text den error tekst der skal vises til brugeren
     */
    private void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Håndterer remove knappen
     *
     * @param actionEvent
     */
    public void handleRemoveButton(ActionEvent actionEvent) {
        if (eventmanagerTable.getSelectionModel().getSelectedItem() == null) {
            error("Vælg hvilken person du vil slette");
        } else {
            EventManager eventManager = (EventManager) eventmanagerTable.getSelectionModel().getSelectedItem();
            if (confirmationBox("Er du sikker på at du vil slette " + eventManager.getName() + "?").get() == ButtonType.YES){
                personModel.deleteEventmanager(eventManager, PersonType.EVENTMANAGER);
                eventmanagerTable.getItems().remove(eventManager);
            }
        }
    }

    public void handleAddManagerButton(ActionEvent actionEvent) throws IOException {
        openScene("/dk/easv/GUI/View2/AddEventManager.fxml", true, "Add Eventmanager", false);
        eventmanagerTable.setItems(personModel.getObservablePersons());
    }

    public void handleEditButton(ActionEvent actionEvent) throws IOException, SQLException {
        EventManager eventmanager = (EventManager) eventmanagerTable.getSelectionModel().getSelectedItem();
        if (eventmanager != null) {
            openNewSceneWithPerson(eventmanager, "/dk/easv/GUI/View2/EditEventManager.fxml", "Rediger event manager");
            eventmanagerTable.setItems(personModel.getObservablePersons());
            eventInformationTable.setItems(eventModel.getObservableEvents());
        }else{
            error("Vælg en event manager og prøv igen");
        }
    }


    public void handleBtnClose(ActionEvent actionEvent) throws IOException {
        closeWindow(closeBtn);
        openScene("/dk/easv/GUI/View/LoginView.fxml",false, "Loginscreen",false);
    }

    public void handleSearch(KeyEvent keyEvent) throws SQLException {
        String searchParam = searchTxt.getText();
        ObservableList<Event> foundUserList =  eventModel.searchEvents(eventInformationTable.getItems(), searchParam);
        eventInformationTable.setItems(foundUserList);
    }
}

