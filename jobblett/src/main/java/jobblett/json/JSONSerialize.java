package jobblett.json;

import java.io.File;

import jobblett.core.Group;
import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * @param fileLocation the location of the file to write to (relative to jobblett's appdata-folder)
     */
    public JSONSerialize(Object object, String fileLocation) {
        this.object = object;
        this.fileLocation = fileLocation;
    }

    /**
     * Serializes the object.
     */
    public void exportJSON() {
        try {
            // create object mapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
            // convert map file to JSON
            objectMapper.writeValue(new File(fileLocation), object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main(true);
        Group gruppe7 = main.getGroupList().newGroup("Gruppe7");

        main.getUserList().newUser("haryp", "bestePassord123", "Hary", "Pi");
        main.getUserList().newUser("sanketb", "bestePassord123", "Sanket", "Be");
        main.getUserList().newUser("kavus", "bestePassord123", "Lol", "Si");
        main.getUserList().newUser("lol", "bestePassord123", "Njaal", "Te");

        gruppe7.addUser(main.getUserList().getUser("haryp"));
        gruppe7.addUser(main.getUserList().getUser("sanketb"));
        gruppe7.addUser(main.getUserList().getUser("kavus"));
        gruppe7.addUser(main.getUserList().getUser("lol"));

        new JSONSerialize(main,"src/main/resources/jobblett/json/main.json").exportJSON();
    }
}
