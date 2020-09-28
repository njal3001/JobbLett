package jobblett.json;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jobblett.core.Group;
import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import jobblett.core.User;

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
            // create object mapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            // convert map file to JSON
            objectMapper.writeValue(new File(fileLocation), object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main(true);
        Group gruppe7 = main.getGroupList().newGroup("Gruppe7");

        User olav = main.getUserList().newUser("olav", "bestePassord123", "Olav", "Nordmann");
        User nora = main.getUserList().newUser("nora", "bestePassord123", "Nora", "Bekkestad");
        User petter = main.getUserList().newUser("petter", "bestePassord123", "Petter", "Petterson");
        User david = main.getUserList().newUser("david", "bestePassord123", "David", "Berg");

        gruppe7.addUser(olav);
        gruppe7.addUser(nora);
        gruppe7.addUser(petter);
        gruppe7.addUser(david);

        main.getJobShiftList().newJobShift(nora, LocalDateTime.now(), Duration.ofHours(2),"Dette er Olav sin skift.");

        new JSONSerialize(main,"defaultMain.json").exportJSON();
    }
}
