package jobblett.core;

import java.time.Duration;
import java.time.LocalDateTime;

public class JobShift {

    private User user;
    private LocalDateTime startingTime;
    private Duration duration;
    private String info;

    public JobShift(User user, LocalDateTime startingTime, Duration duration, String info){
        this.user = user;
        this.startingTime = startingTime;
        this.duration = duration;
        this.info = info;
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

    public String getInfo(){
        return info;
    }


}