package bolett.json;

import bolett.core.Main;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;


public class JSONDeserialize {
    Main main;

    public Main importJSON() {
        Main main = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/bolett/main.json"));
            String json = reader.readLine();

            // create object mapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // deserialize json string
            main = objectMapper.readValue(json,Main.class);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }

        return main;
    }

    public static void main(String[] args) {
        JSONDeserialize importer = new JSONDeserialize();
        Main main = importer.importJSON();
        System.out.println(main);

    }
}
