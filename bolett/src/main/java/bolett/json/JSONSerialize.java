package bolett.json;

import java.io.File;

import bolett.core.Group;
import bolett.core.GroupList;
import bolett.core.Main;
import bolett.core.UserList;
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
            // convert map file to JSON
            objectMapper.writeValue(new File(fileLocation), object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserList userList = new UserList();
        GroupList groupList = new GroupList();
        Group gruppe7 = groupList.newGroup("Gruppe7");

        userList.newUser("haryp", "bestePassord123", "Hary", "Pi");
        userList.newUser("sanketb", "bestePassord123", "Sanket", "Be");
        userList.newUser("kavus", "bestePassord123", "Lol", "Si");
        userList.newUser("lol", "bestePassord123", "Njaal", "Te");

        gruppe7.addUser(userList.getUser("haryp"));
        gruppe7.addUser(userList.getUser("sanketb"));
        gruppe7.addUser(userList.getUser("kavus"));
        gruppe7.addUser(userList.getUser("lol"));

        new JSONSerialize(userList,"src/main/resources/bolett/json/userList.json").exportJSON();
        new JSONSerialize(groupList,"src/main/resources/bolett/json/groupList.json").exportJSON();
    }
}
