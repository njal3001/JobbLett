package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.core.UserList;

/**
 * Used to serialize Main.class to main.json in the systems user-folder.
 * Saves the data-file in $USER_HOME/.jobblett/main.json
 */
public class JobblettSerializer extends ObjectMapper {


  /**
   * TODO.
   */
  public JobblettSerializer() {
    registerModule(new JavaTimeModule());
    registerModule(new JobblettCoreModule());
    configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /**
   * Serializes the object.
   */
  public void writeValueOnDefaultLocation(Object object) {
    String file = object.getClass().getSimpleName() + ".json";
    String fileLocation = System.getProperty("user.home") + "/.jobblett/" + file;
    try {
      writeValue(new File(fileLocation), object);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Just for generating default json files by the developer.
   *
   * @param object TODO
   * @param file TODO
   */
  private void writeValueOnDefaultLocation(Object object, String file) {
    String fileLocation = System.getProperty("user.home") + "/.jobblett/" + file;
    try {
      writeValue(new File(fileLocation), object);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override public String writeValueAsString(Object value) {
    try {
      return super.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
