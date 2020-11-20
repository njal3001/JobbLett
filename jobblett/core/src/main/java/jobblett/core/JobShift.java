package jobblett.core;

import java.time.Duration;
import java.time.LocalDateTime;

public class JobShift extends PropertyChangeSupporter {

  private User user;
  private LocalDateTime startingTime;
  private Duration duration;
  private String info;

  /**
   * Creates a JobShift with the given parameters.
   *
   * @param user user that the shift is assigned to.
   * @param startingTime starting time of the shift.
   * @param duration duration of the shift.
   * @param info info about the shift.
   */
  public JobShift(User user, LocalDateTime startingTime, Duration duration, String info) {
    setUser(user);
    setStartingTime(startingTime);
    setDuration(duration);
    setInfo(info);
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getStartingTime() {
    return startingTime;
  }

  public Duration getDuration() {
    return duration;
  }

  public LocalDateTime getEndingTime() {
    return startingTime.plus(duration);
  }

  public String getInfo() {
    return info;
  }

  public void setUser(User user) {
    this.user = user;
    firePropertyChange("user", getUser());
  }

  /**
   * Sets the starting time of the job shift.
   *
   * @param startingTime starting time for the job shift.
   */
  public void setStartingTime(LocalDateTime startingTime) {
    this.startingTime = startingTime;
    firePropertyChange("startingTime", getStartingTime());
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
    firePropertyChange("duration", getDuration());
  }

  public void setInfo(String info) {
    this.info = info;
    firePropertyChange("info", getInfo());
  }

  public boolean isOutDated() {
    return this.startingTime.isBefore(LocalDateTime.now());
  }

  @Override
  public String toString() {
    return "JobShift{" + "user=" + user + ", startingTime=" 
      + startingTime + ", duration=" 
      + duration + ", info='" + info + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof JobShift) {
      JobShift jobShift = (JobShift) o;
      if (this.user != null) {
        if (!this.user.equals(jobShift.user)) {
          return false;
        }
      } else if (jobShift.user != null) {
        return false;
      }
      if (!this.startingTime.equals(jobShift.startingTime)) {
        return false;
      }
      if (!this.duration.equals(jobShift.duration)) {
        return false;
      }
      if (!this.info.equals(jobShift.info)) {
        return false;
      }
      return true;
    } else {
      return super.equals(o);
    }
  }

  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
  }
}
