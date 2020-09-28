package jobblett.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.ANY)
public class JobShift {

    private User user;
    private LocalDateTime startingTime;
    private Duration duration;
    private String info;

    @JsonCreator
    public JobShift(@JsonProperty("user") User user,
                    @JsonProperty("startingTime") LocalDateTime startingTime,
                    @JsonProperty("duration") Duration duration,
                    @JsonProperty("info") String info
    ){
        this.user = user;
        this.startingTime = startingTime;
        this.duration = duration;
        this.info = info;
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

    @JsonGetter
    public String getInfo(){
        return info;
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