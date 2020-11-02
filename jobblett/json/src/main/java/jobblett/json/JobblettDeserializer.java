package jobblett.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used to deserialize main.json to Main.class from the systems user-folder. Imports the data-file
 * from $USER_HOME/.jobblett/main.json
 */
public class JobblettDeserializer<Type> {
    ObjectMapper objectMapper;
    Reader reader;
    Class<Type> classType;

    /**
     * Initializes a JobblettDeserializer-instance and reads main.json.
     */
    public JobblettDeserializer(Class<Type> classType) {
        this.classType = classType;
        File f = new File(System.getProperty("user.home") + "/.jobblett");
        // noinspection ResultOfMethodCallIgnored
        boolean mkdir = f.mkdir();
        if (mkdir) useDefaultValues();
        else {
            try {
                Path path = Paths.get(System.getProperty("user.home") + "/.jobblett", classType.getSimpleName()+".json");
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

        objectMapper.registerModule(new JobblettCoreModule());
    }

    private void useDefaultValues() {
        URL url = getClass().getResource("default"+classType.getSimpleName()+".json");
        try {
            this.reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T useDefaultValues(Class <T> classType) {
        URL url = JobblettDeserializer.class.getResource("default"+classType.getSimpleName()+".json");
        Reader reader;
        T object = null;
        try {
            reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JobblettCoreModule());
            object = objectMapper.readValue(reader, classType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Imports main.json and returns a new Main.class instance with tha data.
     *
     * @return Main.class object
     */
    public Type deserialize() {
        Type obj = null;
        try {
            // deserialize json string
            obj =  objectMapper.readValue(reader, classType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    /**
     * Imports from a String
     *
     * @return Main.class object
     */
    public Type deserializeString(String value) {
        Type obj = null;
        try {
            // deserialize json string
            obj = (Type) objectMapper.readValue(value, classType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
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
