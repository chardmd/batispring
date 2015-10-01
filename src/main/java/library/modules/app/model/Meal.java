package library.modules.app.model;


import java.sql.Time;
import java.util.Date;

public class Meal {

    private long id;
    private long userId;
    private Date date;
    private Time time;
    private String description;
    private Long calories;

    public Meal() {

    }

    public Meal(long userId, Date date, Time time, String description, Long calories) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.description = description;
        this.calories = calories;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
