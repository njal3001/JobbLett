package jobblett.core;

import java.time.Duration;
import java.time.LocalDateTime;

public class JobShift implements Comparable<JobShift>{

    private User user;
    private LocalDateTime startingTime;
    private Duration duration;
    private String info;

    public JobShift(User user, LocalDateTime startingTime, Duration duration, String info){
        setUser(user);
        setStartingTime(startingTime);
        setDuration(duration);
        setInfo(info);
    }

    public User getUser(){
        return user;
    }

    public LocalDateTime getStartingTime(){
        return startingTime;
    }

    public Duration getDuration(){
        return duration;
    }

    public LocalDateTime getEndingTime(){
        return startingTime.plus(duration);
    }

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
}