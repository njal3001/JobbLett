package jobblett.restapi;

import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class JobShiftListResource extends RestApiClass {
  protected static final Logger LOG = LoggerFactory.getLogger(JobShiftListResource.class);
  public static final String JOB_SHIFT_LIST_RESOURCE_PATH = "shifts";

  private final Group group;
  private final JobShiftList jobShiftList;

  public JobShiftListResource(Group group) {
    this.group = group;
    this.jobShiftList = group.getJobShiftList();
  }

  /**
   * Returns the JobShiftList.
   *
   * @return JobShiftList-object
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JobShiftList get() {
    debug("Returns the jobShiftList");
    return jobShiftList;
  }

  /**
   * Returns JobShiftResource in the specified index.
   *
   * @param indexString jobShift's index as a string
   * @return JobShiftResource
   */
  @GET
  @Path("/get/{indexString}")
  @Produces(MediaType.APPLICATION_JSON)
  public JobShiftResource getJobShifts(@PathParam("indexString") String indexString) {
    int index = Integer.parseInt(indexString);
    JobShift jobShift = jobShiftList.get(index);
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
  @Path("/remove/{indexString}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeJobShifts(@PathParam("indexString") String indexString) {
    int index = Integer.parseInt(indexString);
    JobShift jobShift = jobShiftList.get(index);
    if (jobShift == null) {
      return false;
    }
    boolean removed = jobShiftList.remove(jobShift);
    LOG.debug("Removed jobShift: " + removed);
    return removed;
  }

  /**
   * Adds a JobShift.
   *
   * @param jobShift jobShift going to be added
   */
  @PUT
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  public void addJobShifts(JobShift jobShift) {
    jobShiftList.add(jobShift);
    LOG.debug("Added jobShift");
  }

  @Override protected Logger logger() {
    return LOG;
  }
}
