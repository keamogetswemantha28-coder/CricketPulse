package za.co.keamogetswe.cricketpulse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/cricketpulse";
    private final static String USER = System.getenv("CRICKETPULSE_DB_USER");
    private static final String PASSWORD = System.getenv("CRICKETPULSE_DB_PASSWORD");

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnector.connect()) {
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
