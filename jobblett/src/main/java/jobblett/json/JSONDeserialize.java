package jobblett.json;

import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class JSONDeserialize {
    Main main;
    BufferedReader reader;
    ObjectMapper objectMapper;
    String json;

    public JSONDeserialize() {

        {
            try {
                reader = new BufferedReader(new FileReader("src/main/resources/jobblett/json/main.json"));
                json = reader.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // create object mapper instance
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
    }

    public Main importJSON() {
        Main main = null;
        try {
            // deserialize json string
            main = objectMapper.readValue(json,Main.class);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }

        return main;
    }

    public void updateMain(Main main) {
        try {
            objectMapper.readerForUpdating(main).readValue(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
