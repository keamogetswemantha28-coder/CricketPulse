package za.co.keamogetswe.cricketpulse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchReader {

    //Read match information
    public void readMatchInfo(JsonNode jsonNode){

            System.out.println("====Match Statistic===");
            System.out.println("City: " + jsonNode.get("info").get("city").asText());
            System.out.println("Date: " + jsonNode.get("info").get("dates").get(0).asText());
            System.out.println("Match type: " + jsonNode.get("info").get("match_type").asText());
            System.out.println("Result: " + jsonNode.get("info").get("outcome").get("result").asText());
            System.out.println();
        }

    public void readPlayers(JsonNode jsonNode){
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


    }

    public List<Delivery> readDeliveries(JsonNode jsonNode){

        //A list to add the deliveries
        ArrayList<Delivery> deliveryList = new ArrayList<>();

        JsonNode innings = jsonNode.get("innings");
        for (JsonNode inning: innings) {
            //System.out.println();
            System.out.println("===Innings " + inning.get("team").asText() + "===");

            JsonNode overs = inning.get("overs");
            for (JsonNode over : overs) {
                //System.out.println();
                System.out.println("===Over " + over.get("over").asInt() + "===");


                JsonNode deliveries = over.get("deliveries");


                for (JsonNode deliveryNode : deliveries) {

                    String playerOut = "";
                    String wicketOut = "";

                    if (deliveryNode.get("wickets") != null){
                        playerOut = deliveryNode.get("wickets").get(0).get("player_out").asText();
                        wicketOut = deliveryNode.get("wickets").get(0).get("kind").asText();
                    }
                    Delivery delivery = new Delivery(
                            deliveryNode.get("actual_delivery").asText(),
                            deliveryNode.get("batter").asText(),
                            deliveryNode.get("bowler").asText(),
                            deliveryNode.get("non_striker").asText(),
                            deliveryNode.get("runs").get("batter").asInt(),
                            deliveryNode.get("runs").get("extras").asInt(),
                            deliveryNode.get("runs").get("total").asInt(),
                            playerOut,
                            wicketOut
                    );
                    deliveryList.add(delivery);
                }
            }
        }
        return deliveryList;
    }

    public int calculateTotalRuns(List<Delivery> deliveryList){
        int totalRuns = 0;
        for (Delivery delivery: deliveryList){
            totalRuns += delivery.getTotalRuns();
        }
        return totalRuns;
    }

    public HashMap<String, Integer> calculateRunsByABatter(List<Delivery> deliveryList){
        HashMap<String, Integer> totalBatterRun = new HashMap<>();
        int batterRuns = 0;

        for (Delivery delivery: deliveryList){
            String name = delivery.getBatter();

            if (totalBatterRun.containsKey(name)){
                batterRuns = totalBatterRun.get(name);
                batterRuns+= delivery.getBatterRuns();

            }else {
                batterRuns =delivery.getBatterRuns();
            }

            totalBatterRun.put(name, batterRuns);
        }
        return totalBatterRun;
    }

    public HashMap<String, Integer> calculateRunsByBowler(List<Delivery> deliveryList){
        HashMap<String, Integer> totalRunsConcededByBowler = new HashMap<>();
        int runConceded = 0;

        for (Delivery delivery: deliveryList){
            String name = delivery.getBowler();

            if (totalRunsConcededByBowler.containsKey(name)){
                runConceded = totalRunsConcededByBowler.get(name);
                runConceded += delivery.getTotalRuns();
            }else {
                runConceded = delivery.getTotalRuns();
            }
            totalRunsConcededByBowler.put(name, runConceded);
        }
        return totalRunsConcededByBowler;
    }

    public HashMap<String, Integer> calculateWicketsByBowler(List<Delivery> deliveryList){
        HashMap<String, Integer> wicketsByBowler = new HashMap<>();
        int numberOfWickets = 0;

        for (Delivery delivery: deliveryList){
            String name = delivery.getBowler();

            if (!delivery.getWicketKind().equals("run out") && !delivery.getWicketKind().isEmpty()){
                if (wicketsByBowler.containsKey(name)){
                    numberOfWickets = wicketsByBowler.get(name);
                    numberOfWickets += 1;
                }else {
                    numberOfWickets =1;
                }
                wicketsByBowler.put(name, numberOfWickets);
            }
        }
        return wicketsByBowler;
    }


    public static void main(String[] args) {
        MatchReader matchReader = new MatchReader();
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = MatchReader.class.getClassLoader().
                getResourceAsStream("data/sample/Match.json")) {

            if (inputStream == null) {
                throw new IOException("File not found");
            }
            JsonNode jsonNode = objectMapper.readTree(inputStream);

            //Call the methods
            matchReader.readMatchInfo(jsonNode);
            matchReader.readPlayers(jsonNode);
            List<Delivery> deliveryList = matchReader.readDeliveries(jsonNode);
            int totalDeliveryRuns = matchReader.calculateTotalRuns(deliveryList);
            System.out.println(totalDeliveryRuns);
            HashMap<String, Integer> batterRuns = matchReader.calculateRunsByABatter(deliveryList);
            System.out.println("Runs by batters" + "\n" +batterRuns);
            HashMap<String, Integer> runsConceded = matchReader.calculateRunsByBowler(deliveryList);
            System.out.println("Runs conceded by each bowler" + "\n" + runsConceded);
            for (Delivery delivery : deliveryList) {
                if (!delivery.getPlayerOut().isEmpty()) {
                    System.out.println();
                    System.out.println("Player out: " + delivery.getPlayerOut());
                    System.out.println("Wicket type: " + delivery.getWicketKind());
                }
            }
            HashMap<String, Integer> Wickets = matchReader.calculateWicketsByBowler(deliveryList);
            System.out.println(Wickets);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


