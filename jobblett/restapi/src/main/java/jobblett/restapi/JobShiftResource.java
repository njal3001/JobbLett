package jobblett.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobShiftResource extends RestApiClass {
  protected static final Logger LOG = LoggerFactory.getLogger(JobShiftResource.class);

  private final JobShift jobShift;
  private final Group group;

  public JobShiftResource(JobShift jobShift, Group group) {
    this.jobShift = jobShift;
    this.group = group;
  }

  /**
   * Returns JobShift.
   *
   * @return JobShiftResource
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JobShift get() {
    debug("Returns the jobShift: " + jobShift);
    return jobShift;
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
