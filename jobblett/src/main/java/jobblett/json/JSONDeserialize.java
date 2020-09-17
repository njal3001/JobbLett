package jobblett.json;

import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class JSONDeserialize {
    Main main;
    ObjectMapper objectMapper;
    String json;
    Reader reader;

    public JSONDeserialize() {
        File f = new File(System.getProperty("user.home")+"/.jobblett");
        //noinspection ResultOfMethodCallIgnored
        f.mkdir();
        try {
            Path path = Paths.get(System.getProperty("user.home")+"/.jobblett","main.json");
            reader = new FileReader(path.toFile());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            useDefaultValues();
        }
        // create object mapper instance
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
    }

    private void useDefaultValues() {
        URL url = getClass().getResource("defaultMain.json");
        try {
            this.reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main importJSON() {
        Main main = null;
        try {
            // deserialize json string
            main = objectMapper.readValue(reader,Main.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return main;
    }

    public void updateMain(Main main) {
        try {
            objectMapper.readerForUpdating(main).readValue(reader,Main.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JSONDeserialize();
    }

}
