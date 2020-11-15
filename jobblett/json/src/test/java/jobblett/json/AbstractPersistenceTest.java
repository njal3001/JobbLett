package jobblett.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jobblett.core.GroupList;
import jobblett.core.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractPersistenceTest<T> {

  Class<T> tClass;
  public AbstractPersistenceTest(Class<T> tClass){
    this.tClass = tClass;
  }

  public abstract T getObject();

  public abstract boolean isEquals(T o1, T o2);

  @Test public void persistenceTest() {

    // Serializing
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JobblettCoreModule());
    String result = "";

    try {
      result = mapper.writeValueAsString(getObject());

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }

    // Deserializing
    try {
      T newObject = mapper.readValue(result, tClass);
      assertTrue(isEquals(newObject, getObject()));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail(e);
    }
  }

}
