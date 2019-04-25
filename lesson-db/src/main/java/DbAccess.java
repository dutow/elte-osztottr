import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class DbAccess {

    public static void main(String[] args) {

        String user = "sa";
        String password = "";
        String url = "jdbc:hsqldb:file:./test_db";

        try (Connection c = DriverManager.getConnection(url,user,password)){
            createTables(c);
            addPerson(c,"X Y", 1970);
            addPerson(c,"Z Z", 1980);
            printPersons(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS people;");
            st.executeUpdate("CREATE TABLE people (id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(80), birthyear INT);");
            System.out.println("Tables created");
        }
    }

    private static void addPerson(Connection conn, String name, int birthyear) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO people (name, birthyear) VALUES (?, ?);")) {
            st.setString(1, name);
            st.setInt(2, birthyear);
            st.addBatch();
            st.executeBatch();
        }
    }

    private static void printPersons(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from PEOPLE;")) {
            while(rs.next()) {
                final String name =rs.getString("name");
                final int byear = rs.getInt("birthyear");
                final int id = rs.getInt("id");
                System.out.println("Id: " + id + " name: " + name + ", birthyear: " + byear);
            }
        }
    }
}