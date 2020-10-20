package jobblett.core;

import java.time.Duration;
import java.time.LocalDateTime;

public class JobShift{

  private User user;
  private LocalDateTime startingTime;
  private Duration duration;
  private String info;

  //Fjerne override startTime og gjøre det sånn at utdaterte shift ikke blir lagd av serializer istedet?
  public JobShift(User user, LocalDateTime startingTime, Duration duration, String info, boolean overrideStartTime) {
    setUser(user);
    setDuration(duration);
    setInfo(info);

    if (overrideStartTime) this.startingTime = startingTime;
    else setStartingTime(startingTime);
  }

  public JobShift(User user, LocalDateTime startingTime, Duration duration, String info) {
    this(user,startingTime,duration,info,false);
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
  }


  public void setStartingTime(LocalDateTime startingTime) {
    if(startingTime.isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("Starting time must be later than the current time");
    this.startingTime = startingTime;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  @Override
  public String toString() {
    return "JobShift{" + "user=" + user + ", startingTime=" + startingTime + ", duration=" + duration + ", info='"
        + info + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof JobShift) {
      JobShift jobsHift = (JobShift) o;
      if (!this.user.equals(jobsHift.user)) return false;
      if (!this.startingTime.equals(jobsHift.startingTime)) return false;
      if (!this.duration.equals(jobsHift.duration)) return false;
      if (!this.info.equals(jobsHift.info)) return false;
      return true;
    }
    else return super.equals(o);
  }

  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
  }
}
