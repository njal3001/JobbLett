package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jobblett.core.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Used to serialize Main.class to main.json in the systems user-folder.
 * Saves the data-file in $USER_HOME/.jobblett/main.json
 */
public class JobblettSerializer extends ObjectMapper{


    public JobblettSerializer() {
        registerModule(new JavaTimeModule());
        registerModule(new JobblettCoreModule());
        configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    /**
     * Serializes the object.
     */
    public void writeValueOnDefaultLocation(Object object) {
        String file = object.getClass().getSimpleName()+".json";
        String fileLocation = System.getProperty("user.home")+"/.jobblett/"+file;
        try {
            writeValue(new File(fileLocation), object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Just for generating default json files by the developer
     * @param object
     * @param file
     */
    private void writeValueOnDefaultLocation(Object object, String file) {
        String fileLocation = System.getProperty("user.home")+"/.jobblett/"+file;
        try {
            writeValue(new File(fileLocation), object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String writeValueAsString(Object value) {
        try {
            return super.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        UserList userList = new UserList();
        GroupList groupList = new GroupList();
        Group gruppe7 = groupList.newGroup("Gruppe7");

        userList.add(new User("olav", "bestePassord123", "Olav", "Nordmann"));
        userList.add(new User("nora", "bestePassord123", "Nora", "Bekkestad"));
        userList.add(new User("petter", "bestePassord123", "Petter", "Petterson"));
        userList.add(new User("david", "bestePassord123", "David", "Berg"));

        gruppe7.addUser(userList.get("olav"));
        gruppe7.addUser(userList.get("nora"));
        gruppe7.addUser(userList.get("petter"));
        gruppe7.addUser(userList.get("david"));

        gruppe7.getJobShifts().add(new JobShift(userList.get("nora"), LocalDateTime.now().plusYears(1), Duration.ofHours(2),"Dette er Olav sin skift."));

        new JobblettSerializer().writeValueOnDefaultLocation(userList,"defaultUserList.json");
        new JobblettSerializer().writeValueOnDefaultLocation(groupList,"defaultGroupList.json");
    }
}
