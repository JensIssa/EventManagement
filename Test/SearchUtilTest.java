import dk.easv.BE.Person;
import dk.easv.BLL.util.SearchUtil;
import dk.easv.DAL.PersonDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

class SearchUtilTest {
    @DisplayName("Search efter person der findes")
    @Test
    void validSearch() throws IOException {
        SearchUtil searchUtil = new SearchUtil();
        PersonDAO personDAO = new PersonDAO();

        ObservableList<Person> inputList = FXCollections.observableArrayList();
        inputList.addAll(personDAO.getAllEventManagers());

        //skab det forventede savar
        List<Person> expectedPersonList = FXCollections.observableArrayList();
        for (Person person : inputList) {
            if (Objects.equals(person.getName(), "Alexander"))
                expectedPersonList.add(person);
        }

        //det aktuelle der kommer ud af søge metoden
        String query = "Alexander";
        List<Person> actualPersonList = searchUtil.searchEventManagers(inputList, query);

        Assertions.assertEquals(expectedPersonList, actualPersonList);
    }

    @DisplayName("Search efter person der ikke findes")
    @Test
    void notFoundSearch() throws IOException {
        SearchUtil searchUtil = new SearchUtil();
        PersonDAO personDAO = new PersonDAO();
        String query = "Ole Ram";

        ObservableList<Person> inputList = FXCollections.observableArrayList();
        inputList.addAll(personDAO.getAllEventManagers());

        //skab det forventede svar hvilket er en tom liste da der ikke er en person i
        // databasen ved navnet "Ole Ram"
        List<Person> expectedPersonList = FXCollections.observableArrayList();

        //det aktuelle der kommer ud af søge metoden
        List<Person> actualPersonList = searchUtil.searchEventManagers(inputList, query);

        Assertions.assertEquals(expectedPersonList, actualPersonList);
    }
}