package ru.eleron.osa.lris.schedule.database.entities;

import ru.eleron.osa.lris.schedule.database.patterns.CompositePatternForTask;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task extends EntityPrototype implements CompositePatternForTask {

    public static final Integer DEFAULT_SCORE = 0;
    public static final Integer DEFAULT_TIME = 0;

    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private Integer score;
    @Column(name = "time")
    private Integer time;

    public Task() {
        this.name = "";
        this.score = 0;
        this.time = 0;
    }

    public Task(String name, Integer score, Integer time) {
        this.name = name;
        this.score = score < 0 ? 0 : score;
        this.time = time < 0 ? 0 : time;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Integer getScore() {
        return score;
    }
    @Override
    public void setScore(Integer score) {
        this.score = score < 0 ? 0 : score;
    }
    @Override
    public Integer getTime() {
        return time;
    }
    @Override
    public void setTime(Integer time) { this.time = time < 0 ? 0 : time; }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (score != null ? !score.equals(task.score) : task.score != null) return false;
        return time != null ? time.equals(task.time) : task.time == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}