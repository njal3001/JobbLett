package jobblett.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class JobblettServiceTest extends JerseyTest{

  protected boolean shouldLog(){
    return false;
  }


  @Override
  protected ResourceConfig configure(){
    final JobblettConfig jobblettConfig = new JobblettConfig();
    if(shouldLog()){
      enable(TestProperties.LOG_TRAFFIC);
      enable(TestProperties.DUMP_ENTITY);
      jobblettConfig.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    }
    return jobblettConfig;
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  private ObjectMapper objectMapper;

  @Override
  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    objectMapper = new JobblettModuleObjectMapperProvider().getContext(getClass());
  }

  @Override
  @AfterEach
  public void tearDown() throws Exception{
    super.tearDown();
  }

  @Test
  public void jobbletttest(){

  }


}
