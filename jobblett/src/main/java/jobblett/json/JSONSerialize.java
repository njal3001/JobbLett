package jobblett.json;

import java.io.File;
import java.net.URL;

import jobblett.core.Group;
import jobblett.core.Main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONSerialize {
    Object object;
    String fileLocation;

    public JSONSerialize(Object object, String file) {
        this.object = object;
        this.fileLocation = System.getProperty("user.home")+"/.jobblett/"+file;
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

    /*public static void main(String[] args) {
        Main main = new Main(true);
        Group gruppe7 = main.getGroupList().newGroup("Gruppe7");

        main.getUserList().newUser("olav", "bestePassord123", "Olav", "Nordmann");
        main.getUserList().newUser("nora", "bestePassord123", "Nora", "Bekkestad");
        main.getUserList().newUser("petter", "bestePassord123", "Petter", "Petterson");
        main.getUserList().newUser("david", "bestePassord123", "David", "Berg");

        gruppe7.addUser(main.getUserList().getUser("olav"));
        gruppe7.addUser(main.getUserList().getUser("nora"));
        gruppe7.addUser(main.getUserList().getUser("petter"));
        gruppe7.addUser(main.getUserList().getUser("david"));

        new JSONSerialize(main,"main.json").exportJSON();
    }*/
}
