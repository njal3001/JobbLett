package jobblett.json;

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
public class JobblettLocalSerializer {
    Object object;
    String fileLocation;

    /**
     * Initializes a new instance of JobblettLocalSerializer and locates the file to serialize to.
     *
     * @param object the object that should be serialized
     * @param file the location of the file to write to (relative to jobblett's appdata-folder)
     */
    private JobblettLocalSerializer(Object object, String file) {
        this.object = object;
        this.fileLocation = System.getProperty("user.home")+"/.jobblett/"+file;
    }

    public JobblettLocalSerializer(Object object) {
        this(object,object.getClass().getSimpleName()+".json");
    }

    /**
     * Serializes the object.
     */
    public void exportJSON() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new JobblettCoreModule());

            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileLocation), object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        UserList userList = new UserList();
        GroupList groupList = new GroupList();
        Group gruppe7 = groupList.newGroup("Gruppe7");

        userList.addUser(new User("olav", "bestePassord123", "Olav", "Nordmann"));
        userList.addUser(new User("nora", "bestePassord123", "Nora", "Bekkestad"));
        userList.addUser(new User("petter", "bestePassord123", "Petter", "Petterson"));
        userList.addUser(new User("david", "bestePassord123", "David", "Berg"));

        gruppe7.addUser(userList.getUser("olav"));
        gruppe7.addUser(userList.getUser("nora"));
        gruppe7.addUser(userList.getUser("petter"));
        gruppe7.addUser(userList.getUser("david"));

        gruppe7.getJobShifts().addJobShift(new JobShift(userList.getUser("nora"), LocalDateTime.now().plusYears(1), Duration.ofHours(2),"Dette er Olav sin skift."));

        new JobblettLocalSerializer(userList,"defaultUserList.json").exportJSON();
        new JobblettLocalSerializer(groupList,"defaultGroupList.json").exportJSON();
    }
}
