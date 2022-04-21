package dk.easv.BLL.Test;

import dk.easv.BE.EventManager;
import dk.easv.BE.Person;
import dk.easv.BLL.util.SearchUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchUtilTest {

    @Test
    void searchEventManagers() throws IOException {

        SearchUtil searchUtil = new SearchUtil();
        ObservableList<Person> personObservableList = FXCollections.observableArrayList();
        EventManager eventManager = new EventManager(1, "hello", null, null, null);
        EventManager eventManager2 = new EventManager(2, "hello2", null, null, null);
        String query = "query";
        personObservableList.add(eventManager);
        personObservableList.add(eventManager2);

        List<Person> expectedPersonList = searchUtil.searchEventManagers(personObservableList, query);

       List<Person> actualPersonList = searchUtil.searchEventManagers(personObservableList, query);

        Assertions.assertEquals(expectedPersonList, actualPersonList);

    }
}