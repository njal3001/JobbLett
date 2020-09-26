package jobblett.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Handles all of the JobShifts created.
 */
public class JobShiftList implements Iterable<JobShift> {
    Collection<JobShift> jobShifts = new ArrayList<>();

    /**
     * Returns JobShifts sorted after startTime.
     *
     * @param userFilter user used to filter jobShifts
     * @return Collection of JobShift
     */
    public Collection<JobShift> getJobShiftsSortedAfterStartTime(User userFilter) {
        Collection<JobShift> jobShifts = getJobShiftsFilteredByUser(userFilter);
        return jobShifts.stream()
                .sorted(Comparator.comparing(JobShift::getStartingTime))
                .collect(Collectors.toList());
    }

    /**
     * Returns JobShifts sorted after startTime.
     *
     * @return Collection of JobShift
     */
    public Collection<JobShift> getJobShiftsSortedAfterStartTime() {
        return getJobShiftsSortedAfterStartTime(null);
    }

    /**
     * Returns JobShifts sorted after userName.
     *
     * @param userFilter userFilter user used to filter jobShifts
     * @return Collection of JobShift
     */
    public Collection<JobShift> getJobShiftsSortedAfterUserName(User userFilter) {
        Collection<JobShift> jobShifts = getJobShiftsFilteredByUser(userFilter);
        return jobShifts.stream()
                .sorted(Comparator.comparing(a -> a.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    /**
     * Returns JobShifts sorted after userName.
     *
     * @return Collection of JobShift
     */
    public Collection<JobShift> getJobShiftsSortedAfterUserName() {
        return getJobShiftsSortedAfterUserName(null);
    }

    /**
     * Returns jobShiftsList filtered by a User.
     *
     * @param userFilter user used to filter jobShifts
     * @return Collection<JobShift>
     */
    public Collection<JobShift> getJobShiftsFilteredByUser(User userFilter) {
        Collection<JobShift> jobShifts;
        if (userFilter != null) jobShifts = this.jobShifts.stream()
                .filter(jobShift -> jobShift.getUser() == userFilter)
                .collect(Collectors.toList());
        else jobShifts = new ArrayList<>(this.jobShifts);
        return jobShifts;
    }

    /**
     * Creates a new jobShift with the given parameters.
     * The jobShift is automatically added to jobShiftList.
     * The created jobShift is returned.
     *
     * @param user the user the jobShift is assigned to
     * @param startingTime startTime of the jobShift
     * @param duration duration of the jobShift
     * @param info info about the jobShift
     * @return JobShift
     */
    public JobShift newJobShift(User user, LocalDateTime startingTime, Duration duration, String info) {
        JobShift jobShift = new JobShift(user, startingTime, duration, info);
        jobShifts.add(jobShift);
        return jobShift;
    }

    /**
     * Removes the jobShift from the list and deletes it from memory.
     * WARNING: the jobShift cannot be recovered.
     *
     * @param jobShift the jobShift to be deleted
     * @return true if deleted, else false
     */
    public boolean deleteJobShift(JobShift jobShift) {
        boolean rtn = jobShifts.remove(jobShift);
        System.gc();
        return rtn;
    }

    @Override
    public Iterator<JobShift> iterator() {
        return jobShifts.iterator();
    }

    @Override
    public String toString() {
        return "JobShiftList{" +
                "jobShifts=" + jobShifts +
                '}';
    }
}
