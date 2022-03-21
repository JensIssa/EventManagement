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
     * @param personType - Persontypen, hvori informationer ønskes hentet fra
     * @return - Liste af den persontype der er blevet valgt
     */
    private List<Person> getAllPerson(PersonType personType){
        ArrayList<Person> allPersons = new ArrayList<>();
        try (Connection connection = dc.getConnection()){
            String sqlStatement = "SELECT Person.ID, Person.email, Person.[name], Person.[password], Person.phoneNumber, [Role].[type]\n" +
                    "FROM Person\n" +
                    "INNER JOIN [Role] ON Person.roleID = [Role].ID WHERE roleID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
           preparedStatement.setInt(1, personType.getI());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int id =resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    int phoneNumber = resultSet.getInt("phoneNumber");
                    switch (personType){
                        case USER ->
                            allPersons.add(new User(id, name,email, password, personType, phoneNumber));
                        case EVENTMANAGER ->
                            allPersons.add(new EventManager(id, name,email, password, personType));
                        case ADMIN ->
                            allPersons.add(new Admin(id, name,email, password, personType));
                }
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return allPersons;
    }


    //Henter alle users
    public List<Person> getAllUsers(){
        return getAllPerson(PersonType.USER);
    }
    
    //Henter alle EventManagers
    public List<Person> getAllEventManagers(){
        return getAllPerson(PersonType.EVENTMANAGER);
    }

    //Henter alle Admins
    public List<Person> getAllAdmins(){
        return getAllPerson(PersonType.ADMIN);
    }


    /**
     * skaber en person i databasen med de givne oplysninger
     * @param name
     * @param email
     * @param password
     * @param usertype
     */
    public void createPerson(String name, String email, String password, PersonType usertype) {
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
    public void createPerson(String name, String email, String password, PersonType usertype,int phoneNumber) {
        try (Connection con = dc.getConnection()) {
            String sql = "INSERT INTO Person(Email,Password,roleID,phoneNumber,Name) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, usertype.getI());
            ps.setInt(4,phoneNumber);
            ps.setString(5, name);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * finder den bruger i databasen der passer med de givne login informationer
     * @param email
     * @param password
     * @return En person hvis der er et match i databasen. null hvis der ikke er et match
     */
    public Person loginUser(String email, String password) {
        try (Connection con = dc.getConnection()) {
            String sql =
                    "Select Person.id, email, password, type, phonenumber, [name]"+
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
                int phoneNumber = rs.getInt("phoneNumber");
                switch (type){
                    case USER: return new User(id,name,mail,pass,type,phoneNumber);
                    case ADMIN: return new Admin(id,name,mail,pass,type);
                    case EVENTMANAGER: return new EventManager(id,name,mail,pass,type);
                    default: System.out.println("fuck");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void deletePerson(Person personToBeDeleted, PersonType personType) {
        try (Connection connection = dc.getConnection()){
            String sql = "DELETE FROM Person" +
                    "INNER JOIN Role ON Person.Role.ID = Role.ID" +
                    "WHERE Person.ID = ? AND Person.RoleID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , personToBeDeleted.getId());
            preparedStatement.setInt(2, personType.getI());
            preparedStatement.execute();

            String sqlTicket = "DELETE FROM Ticket WHERE personID = ? ";
            PreparedStatement preparedStatementTicket = connection.prepareStatement(sqlTicket);
            preparedStatementTicket.setInt(1, personToBeDeleted.getId());
            preparedStatementTicket.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        }

        public void deleteUser(Person person) {
            deletePerson(person, PersonType.USER);
        }
        public void deleteEventManager(Person person){
            deletePerson(person, PersonType.EVENTMANAGER);
        }

    public static void main(String[] args) throws IOException {
        PersonDAO eventManagerDAO = new PersonDAO();

        System.out.println(eventManagerDAO.getAllAdmins());

    }

}
