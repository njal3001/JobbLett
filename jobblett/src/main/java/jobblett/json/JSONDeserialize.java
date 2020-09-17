package jobblett.json;

import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used to deserialize main.json to Main.class from the systems user-folder.
 * Imports the data-file from $USER_HOME/.jobblett/main.json
 */
public class JSONDeserialize {
    Main main;
    BufferedReader reader;
    ObjectMapper objectMapper;
    String json;

    /**
     * Initializes a JSONDeserialize-instance and reads main.json.
     */
    public JSONDeserialize() {
        {
            try {
                reader = new BufferedReader(new FileReader("src/main/resources/jobblett/json/main.json"));
                json = reader.readLine();
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

    /**
     * Imports main.json and returns a new Main.class instance with tha data.
     *
     * @return Main.class object
     */
    public Main importJSON() {
        Main main = null;
        try {
            // deserialize json string
            main = objectMapper.readValue(json,Main.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return main;
    }

    /**
     * Updates an existing Main.class instance with data from main.json
     *
     * @param main the existing Main.class instance
     */
    public void updateMain(Main main) {
        try {
            objectMapper.readerForUpdating(main).readValue(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
