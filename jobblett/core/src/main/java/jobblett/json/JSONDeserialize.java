package jobblett.json;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jobblett.core.Main;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.User;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used to deserialize main.json to Main.class from the systems user-folder. Imports the data-file
 * from $USER_HOME/.jobblett/main.json
 */
public class JSONDeserialize {
    ObjectMapper objectMapper;
    Reader reader;

    /**
     * Initializes a JSONDeserialize-instance and reads main.json.
     */
    public JSONDeserialize() {
        File f = new File(System.getProperty("user.home") + "/.jobblett");
        // noinspection ResultOfMethodCallIgnored
        boolean mkdir = f.mkdir();
        if (mkdir) useDefaultValues();
        else {
            try {
                Path path = Paths.get(System.getProperty("user.home") + "/.jobblett", "main.json");
                reader = new FileReader(path.toFile(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                useDefaultValues();
            }
        }


        // create object mapper instance
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        objectMapper.registerModule(new CoreModule());
    }

    private void useDefaultValues() {
        URL url = getClass().getResource("defaultMain.json");
        try {
            this.reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports main.json and returns a new Main.class instance with tha data.
     *
     * @return Main.class object
     * @deprecated Use updateMain(main) with new Main-instance instead.
     */
    public Main importJSON() {
        Main main = null;
        try {
            // deserialize json string
            main = objectMapper.readValue(reader, Main.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return main;
    }

/*    *//**
     * Updates an existing Main.class instance with data from main.json
     *
     * @param main the existing Main.class instance
     *//*
    public void updateMain(Main main) {
        try {
            objectMapper.readerForUpdating(main).readValue(reader, Main.class);
        } catch (Exception e) {
            System.out.println("Existing data is not found or corrupted. Using default data.");
            useDefaultValues();
            try {
                objectMapper.readerForUpdating(main).readValue(reader, Main.class);
            } catch (IOException er) {
                e.printStackTrace();
                er.printStackTrace();
            }
        }
    }*/

}
