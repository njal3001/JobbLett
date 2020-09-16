package bolett.json;

import java.io.File;

import bolett.core.Group;
import bolett.core.GroupList;
import bolett.core.Main;
import bolett.core.UserList;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONSerialize {
    Object object;
    String fileLocation;

    public JSONSerialize(Object object, String fileLocation) {
        this.object = object;
        this.fileLocation = fileLocation;
    }

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

        new JSONSerialize(main,"src/main/resources/bolett/json/main.json").exportJSON();
    }
}
