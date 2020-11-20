package jobblett.restapi;

import java.util.ArrayList;
import java.util.Collection;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.JobShift;
import jobblett.core.User;
import jobblett.core.UserList;
import org.slf4j.Logger;

public abstract class RestApiClass {

  protected abstract Logger logger();

  protected void debug(String logInfo) {
    logger().debug(logInfo);
    System.out.println("[LOG:DEBUG] " + logInfo);
  }
}
