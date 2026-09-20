package za.co.keamogetswe.cricketpulse;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MatchLoader {

    public int getOrCreateTeam(Connection connection, String teamName) throws SQLException {
        String selectSQL = "SELECT team_id FROM teams WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, teamName);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return results.getInt("team_id");
            }
        }
        String insertSQL = "INSERT INTO teams(name) VALUES (?) RETURNING team_id";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, teamName);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("team_id");
        }
    }

    public int getOrCreatePlayer(Connection connection, String playerName, int teamId) throws
            SQLException {
        String selectPlayer = "SELECT player_id FROM players WHERE name = ? AND team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectPlayer)) {
            statement.setString(1, playerName);
            statement.setInt(2, teamId);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return results.getInt("player_id");
            }
        }
        String insertPlayer = "INSERT INTO players(name, team_id) VALUES(?,?) RETURNING player_id";
        try (PreparedStatement statement = connection.prepareStatement(insertPlayer)) {
            statement.setString(1, playerName);
            statement.setInt(2, teamId);

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("player_id");
        }
    }

    public int insertMatch(Connection connection, int match_number, String match_type, String result, Date match_date,
                           int team1_id, int team2_id) throws SQLException {
        String insertMatch = "INSERT INTO matches(match_number, match_type, result, match_date, team1_id, team2_id) VALUES(?,?,?,?,?,?) RETURNING match_id";

        try (PreparedStatement statement = connection.prepareStatement(insertMatch)) {
            statement.setInt(1, match_number);
            statement.setString(2, match_type);
            statement.setString(3, result);
            statement.setDate(4, match_date);
            statement.setInt(5, team1_id);
            statement.setInt(6, team2_id);

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("match_id");
        }
    }

    public Map<String, Integer> buildPlayerIdMap(Connection connection, JsonNode jsonNode,
                                                 int team1Id, String team1Name,
                                                 int team2Id, String team2Name) throws SQLException {
        Map<String, Integer> playerIdByName = new HashMap<>();
        JsonNode players = jsonNode.get("info").get("players");

        Iterator<Map.Entry<String, JsonNode>> teamsIterator = players.fields();
        while (teamsIterator.hasNext()) {
            Map.Entry<String, JsonNode> team = teamsIterator.next();

            String teamName = team.getKey();
            int teamId;

            if (teamName.equals(team1Name)) {
                teamId = team1Id;
            } else {
                teamId = team2Id;
            }

            for (JsonNode playerNode : team.getValue()) {
                String playerName = playerNode.asText();
                int playerId = getOrCreatePlayer(connection, playerName, teamId);
                playerIdByName.put(playerName, playerId);
            }
        }
        return playerIdByName;
    }

    public void insertDeliveries(Connection connection, int matchId, List<Delivery> deliveries,
                                 Map<String, Integer> playerIdsByName,
                                 int team1Id, String team1Name,
                                 int team2Id, String team2Name) throws SQLException {

        String insertSql = "INSERT INTO deliveries(match_id, innings_number, batting_team_id, over_number, " +
                "actual_delivery_label, batter_id, bowler_id, non_striker_id, batter_runs, extra_runs, " +
                "total_runs, extra_type, player_out_id, wicket_kind) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {

            for (Delivery delivery : deliveries) {

                int battingTeamId;
                if (delivery.getBattingTeamName().equals(team1Name)) {
                    battingTeamId = team1Id;
                } else {
                    battingTeamId = team2Id;
                }

                int batterId = playerIdsByName.get(delivery.getBatter());
                int bowlerId = playerIdsByName.get(delivery.getBowler());
                int nonStrikerId = playerIdsByName.get(delivery.getNonStriker());

                Integer playerOutId = null;
                if (!delivery.getPlayerOut().isEmpty()) {
                    playerOutId = playerIdsByName.get(delivery.getPlayerOut());
                }

                String wicketKind = delivery.getWicketKind().isEmpty() ? null : delivery.getWicketKind();

                statement.setInt(1, matchId);
                statement.setInt(2, delivery.getInningsNumber());
                statement.setInt(3, battingTeamId);
                statement.setInt(4, delivery.getOverNumber());
                statement.setString(5, delivery.getActualDelivery());
                statement.setInt(6, batterId);
                statement.setInt(7, bowlerId);
                statement.setInt(8, nonStrikerId);
                statement.setInt(9, delivery.getBatterRuns());
                statement.setInt(10, delivery.getExtras());
                statement.setInt(11, delivery.getTotalRuns());
                statement.setString(12, delivery.getExtraType());

                if (playerOutId != null) {
                    statement.setInt(13, playerOutId);
                } else {
                    statement.setNull(13, Types.INTEGER);
                }

                statement.setString(14, wicketKind);

                statement.executeUpdate();
            }
        }
    }
}
