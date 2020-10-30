package jobblett.core;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles all of the JobShifts created.
 */
public class JobShiftList extends JobblettList<Integer,JobShift>{

    /**
     * @return List of JobShift
     */
    public List<JobShift> getJobShifts() {
        return stream().collect(Collectors.toList()); // Vet ikke om dette er den beste måten å gjøre dette
    }

    /**
     * Returns jobShiftsList filtered by a User.
     *
     * @param userFilter user used to filter jobShifts
     * @return List<JobShift>
     */
    public List<JobShift> getJobShifts(User userFilter) {
        return filter(jobShift -> jobShift.getUser() == userFilter);
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
