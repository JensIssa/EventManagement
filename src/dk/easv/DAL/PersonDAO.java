package dk.easv.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.BE.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private DatabaseConnector dc;

    public PersonDAO() throws IOException {
        dc = new DatabaseConnector();
    }


    /**
     * Generisk metode til at hente alle personer af den ønskede persontype
     *
     * @param personType - Persontypen, hvori informationer ønskes hentet fra
     * @return - Liste af den persontype der er blevet valgt
     */
    private List<Person> getAllPerson(PersonType personType) {
        ArrayList<Person> allPersons = new ArrayList<>();
        try (Connection connection = dc.getConnection()) {
            String sqlStatement = "SELECT Person.ID, Person.email, Person.[name], Person.[password], Person.phoneNumber, [Role].[type]\n" +
                    "FROM Person\n" +
                    "INNER JOIN [Role] ON Person.roleID = [Role].ID WHERE roleID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, personType.getI());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int phoneNumber = resultSet.getInt("phoneNumber");
                switch (personType) {
                    case USER -> allPersons.add(new User(id, name, email, password, personType, phoneNumber));
                    case EVENTMANAGER -> allPersons.add(new EventManager(id, name, email, password, personType));
                    case ADMIN -> allPersons.add(new Admin(id, name, email, password, personType));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allPersons;
    }


    //Henter alle users
    public List<Person> getAllUsers() {
        return getAllPerson(PersonType.USER);
    }

    //Henter alle EventManagers
    public List<Person> getAllEventManagers() {
        return getAllPerson(PersonType.EVENTMANAGER);
    }

    //Henter alle Admins
    public List<Person> getAllAdmins() {
        return getAllPerson(PersonType.ADMIN);
    }


    /**
     * skaber en person i databasen med de givne oplysninger
     *
     * @param name
     * @param email
     * @param password
     * @param usertype
     */
    public void createGuest(String name, String email, String password, PersonType usertype) {
        try (Connection con = dc.getConnection()) {
            String sql = "INSERT INTO Person(Email,Password,roleID,Name) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, usertype.getI());
            ps.setString(4, name);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Tilføjer en person til vores database med de givne oplysninger - Password er sat som default til "Humpty dumpty"
     *
     * @param name
     * @param email
     * @param usertype
     * @param phoneNumber
     */
    public void createGuest(String name, String email, PersonType usertype, int phoneNumber) {
        try (Connection con = dc.getConnection()) {
            String sql = "INSERT INTO Person(Email,password,roleID,phoneNumber,Name) VALUES (?,'Humpty dumpty',?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, usertype.getI());
            ps.setInt(3, phoneNumber);
            ps.setString(4, name);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * finder den bruger i databasen der passer med de givne login informationer
     *
     * @param email
     * @param password
     * @return En person hvis der er et match i databasen. null hvis der ikke er et match
     */
    public Person loginUser(String email, String password) {
        try (Connection con = dc.getConnection()) {
            String sql =
                    "Select Person.id, email, password, type, [name]" +
                            "from Person INNER JOIN Role ON Person.RoleID = Role.ID where email = ? and password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PersonType type = PersonType.valueOf(rs.getString("type"));
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String mail = rs.getString("email");
                String pass = rs.getString("password");
                switch (type) {
                    case ADMIN -> {
                        return new Admin(id, name, mail, pass, type);
                    }
                    case EVENTMANAGER -> {
                        return new EventManager(id, name, mail, pass, type);
                    }
                }
                ;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Opdaterer eventManagers oplysninger
     *
     * @param eventManager - eventManagaren der bliver opdateret
     */
    public void updateEventManagers(EventManager eventManager) {
        try (Connection connection = dc.getConnection()) {
            String sql = "UPDATE Person SET Name=?, email=?, password=? WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eventManager.getName());
            preparedStatement.setString(2, eventManager.getEmail());
            preparedStatement.setString(3, eventManager.getPassword());
            preparedStatement.setInt(4, eventManager.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Denne metode sletter tickets til event, sletter Event og sletter eventmanageren
     * @param eventManager
     */
    public void deleteEventManager(EventManager eventManager) {
        try (Connection connection = dc.getConnection()) {
            List<Integer> eventIDS = new ArrayList<>();

            String sqlFindId = "SELECT ID FROM Event WHERE personID = ? ";
            PreparedStatement preparedStmt = connection.prepareStatement(sqlFindId);
            preparedStmt.setInt(1, eventManager.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                eventIDS.add(id);
            }

            String deleteTicket = "DELETE FROM Ticket WHERE eventID = ?";
            PreparedStatement ps = connection.prepareStatement(deleteTicket);
            for (Integer eventID : eventIDS) {
                ps.setInt(1, eventID);
                ps.addBatch();
            }
            ps.executeBatch();


            String deleteEvent = "DELETE FROM Event WHERE personID = ?";
            PreparedStatement ps1 = connection.prepareStatement(deleteEvent);
            ps1.setInt(1, eventManager.getId());
            ps1.execute();

            String deleteEventmanager = "DELETE FROM Person WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteEventmanager);
            preparedStatement.setInt(1, eventManager.getId());
            preparedStatement.execute();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Sletter en user fra hele systemet - inklusiv alle sine billeter
     *
     * @param userToBeDeleted - Useren der skal slettes
     */
    public void deleteUser(User userToBeDeleted) {
        try (Connection connection = dc.getConnection()) {
            String sql = "DELETE FROM Ticket WHERE personID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userToBeDeleted.getId());
            preparedStatement.execute();
            String sqlUser = "DELETE \n" +
                    "FROM Person \n" +
                    "WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.setInt(1, userToBeDeleted.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Opdaterer userens oplsyninger
     *
     * @param user - useren der bliver opdateret
     */
    public void updateUser(User user) {
        try (Connection connection = dc.getConnection()) {
            String sql = "UPDATE Person SET Name=?, email=?, phoneNumber=? WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getPhoneNumber());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public Integer getMostRecentId() {
        try (Connection con = dc.getConnection()) {
            String sql = "select MAX(id) as id from person";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

