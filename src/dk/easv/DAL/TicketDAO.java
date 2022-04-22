package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.AgeGroup;
import dk.easv.BE.Event;
import dk.easv.BE.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private DatabaseConnector dc;

    public TicketDAO() throws IOException {
        dc = new DatabaseConnector();
    }

    /**
     * Finder alle billet Id'er der er bilknyttet en gæst
     * @param user
     * @return en list af Integers
     * @throws SQLServerException
     */
    public List<Integer> getTicketId(User user) throws SQLServerException {
        List<Integer> ticketList = new ArrayList<>();

        try (Connection connection = dc.getConnection()) {
            String sqlStatement = "SELECT ID FROM Ticket WHERE guestId =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tempId = resultSet.getInt("ID");
                ticketList.add(tempId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ticketList;
    }

    /**
     * Finder hvor mange gæster der deltager i det inputtede event
     * af den inputted alders gruppe
     * @param event
     * @param ageGroup
     * @return en int
     * @throws SQLException
     */
    public int  countEventAttendees(Event event, AgeGroup ageGroup) throws SQLException {
        try (Connection connection = dc.getConnection()){
            String sql = "SELECT COUNT (age) AS amount\n" +
                    "FROM Ticket\n" +
                    "WHERE eventID = ? AND age = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.setInt(2, ageGroup.getI());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("amount");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * finder hvor mange billetter den indputtede user
     * har tilknyttet til sig af den indputtede aldersgruppe
     * @param user
     * @param ageGroup
     * @return int
     */
    public int countIndividualGuestAttendees(User user, AgeGroup ageGroup){
        try (Connection connection = dc.getConnection()){
            String sql = "SELECT COUNT (age) AS amount\n" +
                    "FROM Ticket\n" +
                    "WHERE guestID = ? AND age = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, ageGroup.getI());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("amount");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Opdatere hvor mange billetter der er tilknyttet til
     * den givne gæst og event
     * @param user
     * @param event
     * @param ageGroup
     * @param ticketAmount
     * @throws SQLException
     */
    public void updateTicket(User user, Event event, AgeGroup ageGroup, int ticketAmount) throws SQLException {
        try(Connection connection = dc.getConnection()){
            String sql = "DELETE FROM Ticket WHERE guestID=? AND age=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, ageGroup.getI());
            preparedStatement.execute();
            String sqlAdd = "INSERT INTO Ticket (guestID, eventID, age) VALUES (?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlAdd);
            preparedStatement1.setInt(1, user.getId());
            preparedStatement1.setInt(2, event.getId());
            preparedStatement1.setInt(3, ageGroup.getI());
            for (int i = 0; i < ticketAmount; i++) {
                preparedStatement1.addBatch();
            }
            preparedStatement1.executeBatch();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
