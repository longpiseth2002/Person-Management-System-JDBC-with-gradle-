package utils;

public class SQLUtils {
    public static class PersonSQL {

        // all constant must in uppercase
        public static final String user = "SELECT * FROM user_tb WHERE username = ? AND password = ?";

        public static final String getAllPersonSql = """
                select * from person_tb;
                """;
        public static final String insertNewPerson = "INSERT INTO person_tb (fullname, gender, email, address) VALUES (?, ?, ?, ?)";

        public static final String searchCountry = "SELECT * FROM person_tb WHERE fullname = ?";
        public static final String searchGender = "SELECT * FROM person_tb WHERE gender = ?";
        public static final String deletePersonById = """
                delete from person_tb where id = ?
                """;

        public static final String updatePerson = """
                update person_tb set  fullname=?,gender=?,email=?,address=?
                where id = ?
                """;


        public static final String searchPerson = """
                select * from person_tb
                where id = ?
                """;
    }
}
