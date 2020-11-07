package jobblett.json;

import static jobblett.json.JobblettSerializer.JOBBLETT_DATA_DIRECTORY;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Used to deserialize main.json to Main.class from the systems user-folder. Imports the data-file
 * from $USER_HOME/.jobblett/main.json
 */
public class JobblettDeserializer {

  /**
   * Deserializes Jobblett-objects from default values.
   * These default values are stored in json-resources.
   *
   * @param classType class type of object to be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public static <T> T useDefaultValues(Class<T> classType) {
    URL fileUrl = JobblettDeserializer.class.getResource("default"
        + classType.getSimpleName()
        + ".json");
    try {
      return deserialize(classType, fileUrl);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Deserializes Jobblett-objects from existing data.
   * Returns default values if data doesn't exist.
   *
   * @param classType class type of object to be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public static <T> T deserialize(Class<T> classType) {
    File dir = new File(JOBBLETT_DATA_DIRECTORY);
    if (!dir.exists()) {
      System.out.println("Jobblett data folder does not exist. Starting clean with default data.");
      return useDefaultValues(classType);
    }
    String fileName = classType.getSimpleName() + ".json";
    File file = new File(dir + "/" + fileName);

    try {
      return deserialize(classType, file.toURI().toURL());
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Something went wrong while using existing data.");
      System.out.println("Using default values instead.");
      return useDefaultValues(classType);
    }
  }

  private static <T> T deserialize(Class<T> classType, URL fileUrl) throws IOException {
    T obj = null;
    Reader reader = new InputStreamReader(fileUrl.openStream(), StandardCharsets.UTF_8);
    obj = getObjectMapper().readValue(reader, classType);
    return obj;
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new JobblettCoreModule());
    return objectMapper;
  }

  /**
   * Deserializes Jobblett-objects from a string.
   *
   * @param classType class type of object to be deserialized
   * @param value string value that should be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public static <T> T deserializeString(Class<T> classType, String value) {
    T obj = null;
    try {
      obj = getObjectMapper().readValue(value, classType);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return obj;
  }
}
