package za.co.keamogetswe.cricketpulse;

import com.xxdb.DBConnection;

import java.io.IOException;

public class DolphinConnector {
    public static void main(String[] args) throws IOException {
        DBConnection conn = new DBConnection();
        boolean success = conn.connect("localhost", 8848, "admin", "123456");
        System.out.println("Connected: " + success);
    }
}
