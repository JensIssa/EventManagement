package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.Event;
import dk.easv.BE.EventManager;
import dk.easv.BE.User;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EventDAO {
    private DatabaseConnector dc;

    public EventDAO() throws IOException {
        dc = new DatabaseConnector();
    }

    /**
     * Henter en liste af alle events i databasen
     * @return en liste af alle events
     * @throws SQLException
     */
    private List<Event> getAllEvents() throws SQLException {
        ArrayList<Event> allEvents = new ArrayList<>();
        try (Connection connection = dc.getConnection()) {
            String sqlStatement = "SELECT * FROM Event";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int personId = resultSet.getInt("personID");
                String name = resultSet.getString("name");
                Date startDate = resultSet.getDate("startDate");
                String startTime = resultSet.getString("startTime");

                String sqlGetManagerName = "SELECT name FROM Person WHERE ID = ?";
                PreparedStatement psGetMName = connection.prepareStatement(sqlGetManagerName);
                psGetMName.setInt(1,personId);
                ResultSet rsGetMName = psGetMName.executeQuery();
                rsGetMName.next();
                String managerName = rsGetMName.getString("name");

                Event event = new Event(id, personId, name, startDate.toLocalDate(), startTime, managerName);
                allEvents.add(event);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allEvents;
    }


    /**
     * Opretter et event objekt i databasen.
     *
     * @param eventManager eventmanageren der opretter eventet
     * @param name         navnet på eventet
     * @param startDate    Datoen på eventstart
     * @param startTime    Tiden på starten af eventet
     * @throws SQLServerException
     */
    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime) throws SQLServerException {
        String sql = "INSERT INTO Event(personID, name, startDate, startTime) VALUES (?,?,?, ?)";
        try (Connection connection = dc.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, eventManager.getId());
            ps.setString(2, name);
            ps.setDate(3, Date.valueOf(startDate));
            ps.setString(4, startTime);
            ps.addBatch();
            ps.executeBatch();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    
}
