package jobblett.restapi;

import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/update")
  public void Update(JobShift jobShift) {
    User user = group.getUser(jobShift.getUser().getUsername());
    this.jobShift.setUser(user);
    this.jobShift.setDuration(jobShift.getDuration());
    this.jobShift.setInfo(jobShift.getInfo());
    this.jobShift.setStartingTime(jobShift.getStartingTime());
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
