package jobblett.restapi;

import org.slf4j.Logger;

public abstract class RestApiClass {

  protected abstract Logger logger();

  protected void debug(String logInfo) {
    logger().debug(logInfo);
    System.out.println("[LOG:DEBUG] " + logInfo);
  }
}
