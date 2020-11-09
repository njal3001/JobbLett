package jobblett.core;

import java.io.Serializable;
import java.util.Comparator;

public class JobShiftStartTimeComparator implements Comparator<JobShift>, Serializable {

  @Override public int compare(JobShift o1, JobShift o2) {
    return o1.getStartingTime().compareTo(o2.getStartingTime());
  }
}
