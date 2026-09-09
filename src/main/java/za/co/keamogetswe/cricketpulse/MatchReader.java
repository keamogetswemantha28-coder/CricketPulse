package za.co.keamogetswe.cricketpulse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
            //A list to add the deliveries
            ArrayList<Delivery> deliveryList = new ArrayList<>();

            JsonNode innings = jsonNode.get("innings");
            for (JsonNode inning: innings){
                System.out.println();
                System.out.println("===Innings " + inning.get("team").asText() + "===");

                JsonNode overs = inning.get("overs");
                for (JsonNode over: overs){
                    System.out.println();
                    System.out.println("===Over " + over.get("over").asInt() + "===");


                    JsonNode deliveries = over.get("deliveries");


                    for (JsonNode deliveryNode : deliveries){
                        Delivery delivery = new Delivery(
                                deliveryNode.get("actual_delivery").asText(),
                                deliveryNode.get("batter").asText(),
                                deliveryNode.get("bowler").asText(),
                                deliveryNode.get("non_striker").asText(),
                                deliveryNode.get("runs").get("batter").asInt(),
                                deliveryNode.get("runs").get("extras").asInt(),
                                deliveryNode.get("runs").get("total").asInt()
                        );
                        deliveryList.add(delivery);
                    }
                }
            }
            System.out.println("===Delivery Statistics===");
            System.out.println("Total deliveries: " + deliveryList.size());
            System.out.println("First Delivery Batter: " + deliveryList.get(0).getBatter());
            System.out.println("First Delivery Runs: " + deliveryList.get(0).getBatterRuns());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
