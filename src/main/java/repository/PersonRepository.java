package repository;

import model.Person;
import utils.PropertyUtils;
import utils.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonRepository {
    private final Properties properties;
    private static final Logger logger = Logger.getLogger(PersonRepository.class.getName());

    public PersonRepository() {
        properties = PropertyUtils.loadProperty();
    }

    private Connection startDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("DB_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD")
        );
    }


    public boolean authenticateUser(String username, String password) {
        try (Connection connection = startDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLUtils.PersonSQL.user)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during user authentication", e);
        }
        return false;
    }


    public List<Person> getAllPerson() {
        try (
                Connection connection = startDatabaseConnection();
                Statement statement = connection.createStatement();
        ) {
            var personList = new ArrayList<Person>();
            var rs = statement.executeQuery(SQLUtils.PersonSQL.getAllPersonSql);
            while (rs.next()) {
                personList.add(
                        new Person()
                                .setId(rs.getInt("id"))
                                .setFullName(rs.getString("fullname"))
                                .setEmail(rs.getString("email"))
                                .setAddress(rs.getString("address"))
                                .setGender(rs.getString("gender"))
                );
            }
            return personList;

        } catch (SQLException ex) {
            System.out.println("Failed to retreive all the person data ! ");
            ex.printStackTrace();
        }

        return null;
    }

    public int addNewPerson(Person person) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.insertNewPerson, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, person.getFullName());
            ps.setString(2, person.getGender());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getAddress());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
            return affectedRows;
        } catch (SQLException ex) {
            System.out.println("Error when adding a new person");
            ex.printStackTrace();
            return 0;
        }
    }

    public int updatePerson(Person updatedPerson) {
        try
                (
                        Connection connection = startDatabaseConnection();
                        PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.updatePerson)
                ) {

            ps.setString(1, updatedPerson.getFullName());
            ps.setString(2, updatedPerson.getGender());
            ps.setString(3, updatedPerson.getEmail());
            ps.setString(4, updatedPerson.getAddress());
            ps.setInt(5, updatedPerson.getId());

            return ps.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public int deletePersonByID(int personID) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.deletePersonById)

        ) {
            ps.setInt(1, personID);
            return ps.executeUpdate(); // return int -> number of records that we deleted !

        } catch (SQLException ex) {
            System.out.println("Failed to delete the person record with ID = " + personID);
            ex.printStackTrace();
            return 0;
        }

    }

    public List<Person> getPersonsByGender(String genderToSearch) throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = startDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(SQLUtils.PersonSQL.searchGender)) {
            statement.setString(1, genderToSearch);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setFullName(resultSet.getString("fullname"));
                    person.setGender(resultSet.getString("gender"));
                    person.setEmail(resultSet.getString("email"));
                    person.setAddress(resultSet.getString("address"));
                    persons.add(person);
                }
            }
        }
        return persons;
    }

    public List<Person> getPersonsByName(String nameToSearch) throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = startDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(SQLUtils.PersonSQL.searchCountry)) {
            statement.setString(1, nameToSearch);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setFullName(resultSet.getString("fullname"));
                    person.setGender(resultSet.getString("gender"));
                    person.setEmail(resultSet.getString("email"));
                    person.setAddress(resultSet.getString("address"));
                    persons.add(person);
                }
            }
        }
        return persons;
    }

}
