package jobblett.core;

import com.fasterxml.jackson.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class JobShift{

  private User user;
  private LocalDateTime startingTime;
  private Duration duration;
  private String info;

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
  }

  public void setStartingTime(LocalDateTime startingTime) {
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
}
