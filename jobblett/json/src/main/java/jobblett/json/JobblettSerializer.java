package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;

/**
 * Used to serialize Main.class to main.json in the systems user-folder.
 * Saves the data-file in $USER_HOME/.jobblett/main.json
 */
public class JobblettSerializer {

  public static final String JOBBLETT_DATA_DIRECTORY =
      System.getProperty("user.home") + "/.jobblett";

  ObjectMapper objectMapper = new ObjectMapper();

  /**
   * TODO.
   */
  public JobblettSerializer() {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new JobblettCoreModule());
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /**
   * Serializes an object and saves it as a file in jobblett data directory.
   * The filename will be the object's simple classname.
   *
   * @param object value to be serialized
   */
  public void writeValueOnDefaultLocation(Object object) {
    File dir = new File(JOBBLETT_DATA_DIRECTORY);
    boolean created = dir.mkdir(); // Only creates a directory if it doesn't already exist.
    if (created) {
      System.out.println("New directory was created at \"" + dir + "\".");
    }
    if (!dir.isDirectory()) {
      System.out.println("Could not save. The path " + dir + " is not available.");
    }
    String fileName = object.getClass().getSimpleName() + ".json";
    File file = new File(dir + "/" + fileName);
    try {
      objectMapper.writeValue(file, object);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Returns an serialized object as a string.
   *
   * @param value to be serialized
   * @return serialized value
   */
  public String writeValueAsString(Object value) {
    try {
      return objectMapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
