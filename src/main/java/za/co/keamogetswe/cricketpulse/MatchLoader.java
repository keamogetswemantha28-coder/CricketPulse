package za.co.keamogetswe.cricketpulse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchLoader {

    public int getOrCreateTeam(Connection connection, String teamName) throws SQLException {
        String selectSQL = "SELECT team_id FROM teams WHERE name = ?";

        try(PreparedStatement statement = connection.prepareStatement(selectSQL)){
            statement.setString(1, teamName);

            ResultSet results = statement.executeQuery();
            if(results.next()){
                return results.getInt("team_id");
            }
        }
        String insertSQL = "INSERT INTO teams(name) VALUES (?) RETURNING team_id";
        try(PreparedStatement statement = connection.prepareStatement(insertSQL)){
            statement.setString(1, teamName);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("team_id");
        }
    }
}
