package jobblett.core;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles all of the JobShifts created.
 */
public class JobShiftList extends JobblettList<Integer, JobShift> {

  /**
   * TODO.
   *
   * @return List of JobShift TODO
   */
  public List<JobShift> getJobShifts() {
    // TODO: Vet ikke om dette er den beste måten å gjøre dette
    return stream().collect(Collectors.toList());
  }

  /**
   * Returns jobShiftsList filtered by a User.
   *
   * @param userFilter user used to filter jobShifts
   * @return TODO
   */
  public List<JobShift> getJobShifts(User userFilter) {
    return filter(jobShift -> jobShift.getUser() == userFilter);
  }

  @Override protected Integer identifier(JobShift type) {
    return indexOf(type);
  }

  @Override protected Comparator<JobShift> optionalComparator() {
    return new JobShiftStartTimeComparator();
  }

}
