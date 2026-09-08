package za.co.keamogetswe.cricketpulse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MatchReader {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try(InputStream inputStream = MatchReader.class.getClassLoader().
                getResourceAsStream("data/sample/Match.json")){

            if (inputStream == null){
                throw new IOException("File not found");
            }
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            System.out.println("====Match Statistic===");
            System.out.println("City: "+ jsonNode.get("info").get("city").asText());
            System.out.println("Date: "+ jsonNode.get("info").get("dates").get(0).asText());
            System.out.println("Match type: "+ jsonNode.get("info").get("match_type").asText());
            System.out.println("Result: "+ jsonNode.get("info").get("outcome").get("result").asText());
            System.out.println();
            System.out.println("===Teams===");
            JsonNode players = jsonNode.get("info").get("players");
            players.fields().forEachRemaining(team  ->{
                System.out.println(team.getKey());
                System.out.println("===Members===");
                for (JsonNode player : team.getValue()){
                    System.out.println(player.asText());
                }
                System.out.println();
            });
            JsonNode innings = jsonNode.get("innings");
            for (JsonNode inning: innings){
                System.out.println();
                System.out.println("===Innings " + inning.get("team").asText() + "===");

                JsonNode overs = inning.get("overs");
                for (JsonNode over: overs){
                    System.out.println();
                    System.out.println("===Over " + over.get("over").asInt() + "===");

                    JsonNode deliveries = over.get("deliveries");
                    for (JsonNode delivery: deliveries){
                        System.out.println("Actual Delivery: "+ delivery.get("actual_delivery").asText());
                        System.out.println("Batter: " + delivery.get("batter").asText());
                        System.out.println("Bowler: " + delivery.get("bowler").asText());
                        System.out.println("Non_Striker: "+ delivery.get("non_striker").asText());
                        System.out.println("Batter runs: " + delivery.get("runs").get("batter").asInt());
                        System.out.println("Extras: " + delivery.get("runs").get("extras").asInt());
                        System.out.println("Total runs: " + delivery.get("runs").get("total").asInt());
                        System.out.println();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
