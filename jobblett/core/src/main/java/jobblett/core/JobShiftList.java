package jobblett.core;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

/**
 * Handles all of the JobShifts created.
 */
public class JobShiftList extends AbstractList<Integer, JobShift> {

  /**
   * Gets a list of the job shifts.
   *
   * @return List of JobShift
   */
  public List<JobShift> getJobShifts() {
    return stream().collect(Collectors.toList());
  }

  /**
   * Returns jobShiftsList filtered by a User.
   *
   * @param userFilter user used to filter jobShifts
   * @return filtered list
   */
  public List<JobShift> getJobShifts(User userFilter) {
    return filter(jobShift -> jobShift.getUser() == userFilter);
  }

  /**
   * Deletes outdated job shifts.
   */
  public void deleteOutdatedJobShifts() {
    Collection<JobShift> outdatedShifts = stream().filter(JobShift::isOutDated)
        .collect(Collectors.toList());
    System.out.println(outdatedShifts);
    outdatedShifts.forEach((shift) -> remove(shift));
  }

  @Override
  protected Integer identifier(JobShift type) {
    return indexOf(type);
  }

  @Override
  protected Comparator<JobShift> optionalComparator() {
    return new JobShiftStartTimeComparator();
  }

}
