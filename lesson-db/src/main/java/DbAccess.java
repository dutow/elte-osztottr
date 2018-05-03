import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class DbAccess {

    public static void main(String[] args) throws ClassNotFoundException {

        String user = "sa";
        String password = "";
        String url = "jdbc:hsqldb:file:./test_db";

        try (Connection c = DriverManager.getConnection(url,user,password)){
            createTables(c);
            addPerson(c,"X Y", 1970);
            printPersons(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement()) {
            st.executeUpdate("drop table if exists people;");
            st.executeUpdate("create table people (name varchar(80), birthyear int);");
            System.out.println("Tables created");
        }
    }

    private static void addPerson(Connection conn, String name, int birthyear) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("insert into people values (?, ?);")) {
            st.setString(1, name);
            st.setInt(2, birthyear);
            st.addBatch();
            st.executeBatch();
        }
    }

    private static void printPersons(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from people;")) {
            while(rs.next()) {
                String name =rs.getString("name");
                int byear = rs.getInt("birthyear");
                System.out.println("Name: " + name + ", birthyear: " + byear);
            }
        }
    }
}