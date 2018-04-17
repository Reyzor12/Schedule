package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task extends EntityPrototype {

    public static final Integer DEFAULT_SCORE = 0;
    public static final Integer DEFAULT_TIME = 0;

    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private Integer score;
    @Column(name = "time")
    private Integer time;
    @OneToMany
    private List<Task> children;

    public Task() {
        this.score = 0;
        this.time = 0;
        this.children = new ArrayList<>();
    }

    public Task(String name, Integer score, Integer time) {
        this.name = name;
        this.score = score < 0 ? 0 : score;
        this.time = time < 0 ? 0 : time;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = (score < 0 ? 0 : score) + getChildrenScore();
    }

    public Integer getChildrenScore() {
        if (hasChildren()) return this.children.stream().mapToInt(Task::getChildrenScore).sum();
        return 0;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = (time < 0 ? 0 : time) + getChildrenTime();
    }

    public Integer getChildrenTime() {
        if (hasChildren()) return this.children.stream().mapToInt(Task::getChildrenTime).sum();
        return 0;
    }

    public List<Task> getChildren() {
        return children;
    }

    public void setChildren(List<Task> taskList) {
        if (taskList != null) this.children = taskList;
    }

    public void addChildren(List<Task> taskList) {
        if (taskList != null) this.children.addAll(taskList);
    }

    public void addChild(Task task) {
        if (task != null) this.children.add(task);
    }

    public void removeChild(Task task) {
        if (task != null) this.children.remove(task);
    }

    public void removeChildren(List<Task> taskList) {
        if (taskList != null) this.children.removeAll(taskList);
    }

    public void removeAllChildren() {
        this.children.clear();
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

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