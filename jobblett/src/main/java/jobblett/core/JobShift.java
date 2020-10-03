package jobblett.core;

import com.fasterxml.jackson.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.ANY)
public class JobShift implements Comparable<JobShift>{

    private User user;
    private LocalDateTime startingTime;
    private Duration duration;
    private String info;

    @JsonCreator
    public JobShift(@JsonProperty("user") User user, @JsonProperty("startingTime") LocalDateTime startingTime, @JsonProperty("duration") Duration duration, @JsonProperty("info") String info){
        setUser(user);
        setStartingTime(startingTime);
        setDuration(duration);
        setInfo(info);
    }
    
    @JsonGetter
    public User getUser(){
        return user;
    }

    @JsonGetter
    public LocalDateTime getStartingTime(){
        return startingTime;
    }

    @JsonGetter
    public Duration getDuration(){
        return duration;
    }

    @JsonIgnore
    public LocalDateTime getEndingTime(){
        return startingTime.plus(duration);
    }

    @JsonGetter
    public String getInfo(){
        return info;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setStartingTime(LocalDateTime startingTime){
        this.startingTime = startingTime;
    }

    public void setDuration(Duration duration){
        this.duration = duration;
    }

    public void setInfo(String info){
        this.info = info;
    }
    
    /**
     * {@inheritDoc}
     * @return a negative integer, zero, or a positive integer as the starting time of this job shift
     * is less than, equal to, or greater than the starting time of the other job shift.
     */
    @Override
    public int compareTo(JobShift other) {
        if(this.startingTime.isAfter(other.startingTime))
            return 1;
        else if(this.startingTime.isBefore(other.startingTime))
            return -1;
        else
            return 0;
    }
    
    @Override
    public String toString() {
        return "JobShift{" +
                "user=" + user +
                ", startingTime=" + startingTime +
                ", duration=" + duration +
                ", info='" + info + '\'' +
                '}';
    }
}