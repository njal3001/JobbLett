package jobblett.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used to deserialize main.json to Main.class from the systems user-folder. Imports the data-file
 * from $USER_HOME/.jobblett/main.json
 */
public class JobblettDeserializer<T> {
  ObjectMapper objectMapper;
  Reader reader;
  Class<?> classType;

  /**
   * Initializes a JobblettDeserializer-instance and reads main.json.
   */
  public JobblettDeserializer(Class<T> classType) {
    this.classType = classType;


    // create object mapper instance
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    objectMapper.registerModule(new JobblettCoreModule());
  }

  private void useDefaultValues() {
    URL url = getClass().getResource("default" + classType.getSimpleName() + ".json");
    try {
      this.reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Imports main.json and returns a new Main.class instance with tha data.
   *
   * @return Main.class object
   */
  public T deserialize() {

    File f = new File(System.getProperty("user.home") + "/.jobblett");
    // noinspection ResultOfMethodCallIgnored
    boolean mkdir = f.mkdir();
    if (mkdir) {
      useDefaultValues();
    } else {
      try {
        Path path = Paths.get(System.getProperty("user.home") + "/.jobblett",
            classType.getSimpleName() + ".json");
        reader = new FileReader(path.toFile(), StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.out.println(e.getMessage());
        useDefaultValues();
      }
    }

    T obj = null;
    try {
      // deserialize json string
      obj = (T) objectMapper.readValue(reader, classType);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return obj;
  }

  /**
   * Imports from a String.
   *
   * @return Main.class object
   */
  public T deserializeString(String value) {
    T obj = null;
    try {
      // deserialize json string
      obj = (T) objectMapper.readValue(value, classType);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return obj;
  }

}
