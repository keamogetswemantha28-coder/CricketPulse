package za.co.keamogetswe.cricketpulse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MatchReader {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = MatchReader.class.getClassLoader().
                getResourceAsStream("data/sample/Match.json")) {

            if (inputStream == null) {
                throw new IOException("File not found");
            }
            JsonNode jsonNode = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read match information
    public void readMatchInfo(JsonNode jsonNode){

            System.out.println("====Match Statistic===");
            System.out.println("City: " + jsonNode.get("info").get("city").asText());
            System.out.println("Date: " + jsonNode.get("info").get("dates").get(0).asText());
            System.out.println("Match type: " + jsonNode.get("info").get("match_type").asText());
            System.out.println("Result: " + jsonNode.get("info").get("outcome").get("result").asText());
            System.out.println();
        }

}


