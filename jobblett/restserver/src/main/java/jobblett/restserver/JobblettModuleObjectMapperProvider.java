package jobblett.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import jobblett.json.JobblettCoreModule;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class JobblettModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {
  private final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new JobblettCoreModule());

  public JobblettModuleObjectMapperProvider() {
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Override public ObjectMapper getContext(Class<?> classname) {
    return objectMapper;
  }
}
