package za.co.keamogetswe.cricketpulse;

import com.xxdb.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DolphinConnector {

    public static void transferDeliveries(Connection postgresConn, DBConnection dolphinConn) throws SQLException, IOException {
        String selectSql = "SELECT innings_number, over_number, actual_delivery_label, batter_runs, extra_runs, total_runs, wicket_kind FROM deliveries";

        try (PreparedStatement statement = postgresConn.prepareStatement(selectSql)) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int inningsNumber = results.getInt("innings_number");
                int overNumber = results.getInt("over_number");
                String actualDeliveryLabel = results.getString("actual_delivery_label");
                int batterRuns = results.getInt("batter_runs");
                int extraRuns = results.getInt("extra_runs");
                int totalRuns = results.getInt("total_runs");
                String wicketKind = results.getString("wicket_kind");

                String wicketKindValue;
                if (wicketKind == null) {
                    wicketKindValue = "NULL";
                } else {
                    wicketKindValue = "\"" + wicketKind + "\"";
                }

                String insertScript = "insert into t values(" + inningsNumber + ", " + overNumber + ", \"" + actualDeliveryLabel + "\", " + batterRuns + ", " + extraRuns + ", " + totalRuns + ", " + wicketKindValue + ")";
                dolphinConn.run(insertScript);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, SQLException {
        DBConnection conn = new DBConnection();
        boolean success = conn.connect("localhost", 8848, "admin", "123456");
        conn.run("share table(100:0, `innings_number`over_number`actual_delivery_label`batter_runs`extra_runs`total_runs`wicket_kind, [INT, INT, STRING, INT, INT, INT, STRING]) as t");        System.out.println("Connected: " + success);
        Connection postgresConn = DatabaseConnector.connect();
        transferDeliveries(postgresConn, conn);
    }
}
