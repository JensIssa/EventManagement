package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;

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
    public List<Event> getAllEvents() throws SQLException {
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
                String info = resultSet.getString("info");
                String endTime = resultSet.getString("endTime");
                String loc = resultSet.getString("loc");

                String sqlGetManagerName = "SELECT name FROM Person WHERE ID = ?";
                PreparedStatement psGetMName = connection.prepareStatement(sqlGetManagerName);
                psGetMName.setInt(1,personId);
                ResultSet rsGetMName = psGetMName.executeQuery();
                rsGetMName.next();
                String managerName = rsGetMName.getString("name");

                Event event = new Event(id, personId, name, startDate.toLocalDate(), startTime, managerName, info,endTime, loc);
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
    public void createEvent(EventManager eventManager, String name, LocalDate startDate, String startTime, String info, String endTime, String loc) throws SQLServerException {
        String sql = "INSERT INTO Event(personID, name, startDate, startTime, info, endTime, loc ) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = dc.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, eventManager.getId());
            ps.setString(2, name);
            ps.setDate(3, Date.valueOf(startDate));
            ps.setString(4, startTime);
            ps.setString(5, info);
            ps.setString(6, endTime);
            ps.setString(7, loc);
            ps.addBatch();
            ps.executeBatch();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * laver en liste af Strings af emails fra alle users der er tilknyttet det valgte event
     * @param event eventet du vil ha gæste emails fra
     * @return  en liste af strings
     */
    public List<String> getEmailListFromEvent(Event event){
        ArrayList<String> emailList = new ArrayList<>();
        try(Connection connection = dc.getConnection()){
            /*make selecte distinct when testing is done*/
            String sql = "SELECt distinct email from person inner join ticket on guestId = Person.id where eventID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,event.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                String email = resultSet.getString("email");
                emailList.add(email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return emailList;
    }
    /**
     * Sletter en user fra en event
     * @param user - useren der skal fjernes fra eventen
     */
    public void deleteUserFromEvent( User user, Event event){
        try (Connection connection = dc.getConnection()) {
            String sql = "DELETE FROM Ticket WHERE personID = ? and eventid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2,event.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * Tilføjer en user til en event
     * @param event - Eventen useren deltager i
     * @param user - useren der skal tilføjes til eventen
     */
    public void createTicket(Event event, User user, AgeGroup ageGroup, int ticketAmount){
        try (Connection connection = dc.getConnection()) {
            String sql = "INSERT INTO Ticket (guestID, eventID, age) VALUES (?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, event.getId());
            ps.setInt(3, ageGroup.getI());
            for (int i = 0; i < ticketAmount; i++) {
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Finder alle gæster som er tilknyttet et event
     * @param event
     * @return en List af Users
     */
    public List<User> getAllUsersFromEvent(Event event){
        List<User> usersInEvent = new ArrayList<>();
        try (Connection connection = dc.getConnection()) {
                String userSQL = "SELECT distinct person.id, [name],email,password,phonenumber FROM Person\n" +
                        "INNER JOIN Ticket ON Person.ID = Ticket.guestid\n" +
                        "WHERE Ticket.eventID = ?";
                PreparedStatement psUserEvent = connection.prepareStatement(userSQL);
                psUserEvent.setInt(1, event.getId());
                ResultSet rSet = psUserEvent.executeQuery();
              while (rSet.next()){
                  int id = rSet.getInt("ID");
                  String name = rSet.getString("Name");
                  String email = rSet.getString("email");
                  String password = rSet.getString("password");
                  int phoneNumber = rSet.getInt("phoneNumber");
                  User user = new User(id, name,email,password, PersonType.USER, phoneNumber);
                  usersInEvent.add(user);
              }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersInEvent;
    }


    /**
     * Opdatere et events information i databasen
     * @param event det nye event der skal overskrive det gamle
     */
    public void updateEvent(Event event){
        try (Connection connection = dc.getConnection()){
            String sql = "UPDATE Event SET Name=?, startDate=?, startTime=?, info=?, endTime=?, loc=? WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setDate(2, Date.valueOf(event.getStartDate()));
            preparedStatement.setString(3, event.getStartTime());
            preparedStatement.setString(4, event.getInfo());
            preparedStatement.setString(5, event.getEndTime());
            preparedStatement.setString(6, event.getLoc());
            preparedStatement.setInt(7, event.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     * @param eventDelete Denne metode sletter først det valgte movie objekt fra alle categorier og bagefter selve movien fra databasen.
     */
    public void deleteEvent(Event eventDelete) {
        try (Connection connection = dc.getConnection()) {
            String sql = "Delete from Ticket WHERE eventID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,eventDelete.getId());
            ps.execute();

            String sql2 = "DELETE from Event WHERE Id = ?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, eventDelete.getId());
            ps2.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Finder alle events der er tilknyttet til en user
     * @param user
     * @return en List af events
     */
    public List<Event> getAllEventsFromUser(User user){
        List<Event> eventList = new ArrayList<>();
        try (Connection connection = dc.getConnection()) {
            String userSQL = "SELECT * FROM Event\n" +
                    "INNER JOIN Ticket ON Event.ID = Ticket.eventID\n" +
                    "WHERE Ticket.personID = ?";
            PreparedStatement psUserEvent = connection.prepareStatement(userSQL);
            psUserEvent.setInt(1, user.getId());
            ResultSet resultSet = psUserEvent.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int personId = resultSet.getInt("personID");
                String name = resultSet.getString("name");
                Date startDate = resultSet.getDate("startDate");
                String startTime = resultSet.getString("startTime");
                String info = resultSet.getString("info");
                String endTime = resultSet.getString("endtTime");
                String loc = resultSet.getString("loc");

                String sqlGetManagerName = "SELECT name FROM Person WHERE ID = ?";
                PreparedStatement psGetMName = connection.prepareStatement(sqlGetManagerName);
                psGetMName.setInt(1,personId);
                ResultSet rsGetMName = psGetMName.executeQuery();
                rsGetMName.next();
                String managerName = rsGetMName.getString("name");

                Event event = new Event(id, personId, name, startDate.toLocalDate(), startTime, managerName, info, endTime, loc);
                eventList.add(event);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(eventList);
        return eventList;
    }
}
