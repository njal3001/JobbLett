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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobShiftListResource extends RestApiClass {
  protected static final Logger LOG = LoggerFactory.getLogger(JobShiftListResource.class);
  public static final String JOB_SHIFT_LIST_RESOURCE_PATH = "shifts";

  private final Group group;

  public JobShiftListResource(Group group) {
    this.group = group;
  }

  /**
   * Returns the JobShiftList.
   *
   * @return JobShiftList-object
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JobShiftList get() {
    debug("Returns a copy of the jobShiftList");
    return group.getJobShiftList();
  }

  /**
   * Returns JobShiftResource in the specified index.
   *
   * @param indexString jobShift's index as a string
   * @return JobShiftResource
   */
  @Path("/get/{indexString}")
  @Produces(MediaType.APPLICATION_JSON)
  public JobShiftResource getJobShifts(@PathParam("indexString") String indexString) {
    int index = Integer.parseInt(indexString);
    JobShift jobShift = get().get(index);
    LOG.debug("Returns jobShift: " + jobShift);
    return new JobShiftResource(jobShift, group);
  }

  /**
   * Removed a JobShift.
   *
   * @param indexString jobShift's index as a string
   * @return if the shift was removed
   */
  @GET
  @Path("/remove/{adminUsername}/{indexString}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeJobShifts(@PathParam("adminUsername") String adminUsername,
      @PathParam("indexString") String indexString) {
    boolean removed = false;
    int index = Integer.parseInt(indexString);
    JobShift jobShift = get().get(index);
    if (jobShift == null) {
      debug("Removed jobShift: " + removed);
      return false;
    }
    removed = group.removeJobShift(group.getUser(adminUsername), jobShift);
    debug("Removed jobShift: " + removed);
    return removed;
  }

  /**
   * Adds a JobShift.
   *
   * @param jobShift jobShift going to be added
   */
  @PUT
  @Path("/add/{adminUsername}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void addJobShift(@PathParam("adminUsername") String adminUsername, JobShift jobShift) {
    jobShift.setUser(group.getUser(jobShift.getUser().getUsername()));
    group.addJobShift(jobShift, group.getUser(adminUsername));
    debug("Added jobShift");
  }

  /**
   * Deletes outdated jobShifts.
   */
  @PUT
  @Path("/deleteOutdated")
  public void deleteOutdatedJobShifts() {
    group.deleteOutdatedJobShifts();
    debug("Deleted outdated jobShifts");
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
