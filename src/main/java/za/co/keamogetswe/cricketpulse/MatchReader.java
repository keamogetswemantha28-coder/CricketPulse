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
            System.out.println("City: "+ jsonNode.get("info").get("city"));
            System.out.println("Date: "+ jsonNode.get("info").get("dates"));
            System.out.println("Match type: "+ jsonNode.get("info").get("match_type"));
            System.out.println("Results: "+ jsonNode.get("info").get("outcome").get("result"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
