package jobblett.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jobblett.core.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Used to serialize Main.class to main.json in the systems user-folder.
 * Saves the data-file in $USER_HOME/.jobblett/main.json
 */
public class JSONSerialize {
    Object object;
    String fileLocation;

    /**
     * Initializes a new instance of JSONSerialize and locates the file to serialize to.
     *
     * @param object the object that should be serialized
     * @param file the location of the file to write to (relative to jobblett's appdata-folder)
     */
    public JSONSerialize(Object object, String file) {
        this.object = object;
        this.fileLocation = System.getProperty("user.home")+"/.jobblett/"+file;
    }

    /**
     * Serializes the object.
     */
    public void exportJSON() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new CoreModule());

            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileLocation), object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        Group gruppe7 = main.getGroupList().newGroup("Gruppe7");

        main.getUserList().addUser(new User("olav", "bestePassord123", "Olav", "Nordmann"));
        main.getUserList().addUser(new User("nora", "bestePassord123", "Nora", "Bekkestad"));
        main.getUserList().addUser(new User("petter", "bestePassord123", "Petter", "Petterson"));
        main.getUserList().addUser(new User("david", "bestePassord123", "David", "Berg"));

        gruppe7.addUser(main.getUserList().getUser("olav"));
        gruppe7.addUser(main.getUserList().getUser("nora"));
        gruppe7.addUser(main.getUserList().getUser("petter"));
        gruppe7.addUser(main.getUserList().getUser("david"));

        gruppe7.getJobShifts().addJobShift(new JobShift(main.getUserList().getUser("nora"), LocalDateTime.now().plusYears(1), Duration.ofHours(2),"Dette er Olav sin skift."));

        //new JSONSerialize(main,"main.json").exportJSON();
        new JSONSerialize(main,"defaultMain.json").exportJSON();
    }
}
