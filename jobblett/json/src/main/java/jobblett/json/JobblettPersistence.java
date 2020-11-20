package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Used to serialize and deserialize Jobblett-objects.
 */
public class JobblettPersistence {

  public static final String JOBBLETT_DATA_DIRECTORY =
      System.getProperty("user.home") + "/.jobblett";

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Initializing JobblettSerializer by registering necessary modules to the objectMapper.
   */
  public JobblettPersistence() {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new JobblettCoreModule());
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  /**
   * Deserializes Jobblett-objects from default values.
   * These default values are stored in json-resources.
   *
   * @param classType class type of object to be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public <T> T readDefault(Class<T> classType) {
    URL fileUrl = JobblettPersistence.class.getResource("default"
        + classType.getSimpleName()
        + ".json");
    if (fileUrl == null) {
      return null;
    }
    try {
      return readValue(classType, fileUrl);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Deserializes Jobblett-objects from a jsonNode.
   *
   * @param classType class type of object to be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  protected <T> T readValue(Class<T> classType, JsonNode node)
      throws JsonProcessingException {
    return getObjectMapper().readValue(node.toString(), classType);
  }

  /**
   * Deserializes Jobblett-objects from existing data.
   * Returns default values if data doesn't exist.
   *
   * @param classType class type of object to be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public <T> T readValue(Class<T> classType) {
    File dir = new File(JOBBLETT_DATA_DIRECTORY);
    if (!dir.exists()) {
      System.out.println("Jobblett data folder does not exist. Starting clean with default data.");
      return readDefault(classType);
    }
    String fileName = classType.getSimpleName() + ".json";
    File file = new File(dir + "/" + fileName);

    try {
      return readValue(classType, file.toURI().toURL());
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Something went wrong while using existing data.");
      System.out.println("Using default values instead.");
      return readDefault(classType);
    }
  }

  private <T> T readValue(Class<T> classType, URL fileUrl) throws IOException {
    T obj = null;
    Reader reader = new InputStreamReader(fileUrl.openStream(), StandardCharsets.UTF_8);
    obj = getObjectMapper().readValue(reader, classType);
    return obj;
  }

  /**
   * Deserializes Jobblett-objects from a string.
   *
   * @param classType class type of object to be deserialized
   * @param value string value that should be deserialized
   * @param <T> class type of the object to be returned (will be set automatically by classType)
   * @return object deserialized object
   */
  public <T> T readValue(Class<T> classType, String value) {
    T obj = null;
    try {
      obj = getObjectMapper().readValue(value, classType);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return obj;
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
    //just in case there is a file(and not a folder) with the same name
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
   * Serializes an object an returns it as a string.
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
