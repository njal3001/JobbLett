package jobblett.core;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles all of the JobShifts created.
 */
public class JobShiftList implements Iterable<JobShift> {

    List<JobShift> jobShifts = new ArrayList<>();

    /**
     * @return List of JobShift
     */
    public List<JobShift> getJobShifts() {
        return new ArrayList<>(jobShifts); // Vet ikke om dette er den beste måten å gjøre dette
    }

    /**
     * Returns jobShiftsList filtered by a User.
     *
     * @param userFilter user used to filter jobShifts
     * @return List<JobShift>
     */
    public List<JobShift> getJobShifts(User userFilter) {
        return this.jobShifts.stream()
                .filter(jobShift -> jobShift.getUser() == userFilter)
                .collect(Collectors.toList());
    }

    // Jobshift is always sorted by starting time 
    public void addJobShift(JobShift jobShift) {
        jobShifts.add(jobShift);
        Collections.sort(jobShifts, new JobShiftStartTimeComparator());
    }

    /**
     * Removes the jobShift from the list.
     *
     * @param jobShift the jobShift to be deleted
     * @return true if deleted, else false
     */
    public boolean removeJobShift(JobShift jobShift) {
        return jobShifts.remove(jobShift);
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof JobShiftList) {
            JobShiftList jobShiftList = (JobShiftList) o;
            return jobShifts.equals(jobShiftList.jobShifts);
        }
        else return super.equals(o);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
}
