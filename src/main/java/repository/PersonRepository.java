package repository;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import model.Person;
import utils.PropertyUtils;
import utils.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class PersonRepository {
    private final Properties properties;

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
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.insertNewPerson);
        ) {
            ps.setString(1, person.getFullName());
            ps.setString(2, person.getGender());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getAddress());
            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error when adding a new person");
            ex.printStackTrace();
        }
        return 0;
    }

    public int updatePerson(Person updatedPerson) {
        try
                (
                        Connection connection = startDatabaseConnection();
                        PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.updatePerson)
                ) {

            ps.setString(1,updatedPerson.getFullName());
            ps.setString(2,updatedPerson.getGender());
            ps.setString(3,updatedPerson.getEmail());
            ps.setString(4,updatedPerson.getAddress());
            ps.setInt(5,updatedPerson.getId());

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


}
