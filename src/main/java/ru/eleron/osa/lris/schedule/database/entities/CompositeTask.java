package ru.eleron.osa.lris.schedule.database.entities;

import ru.eleron.osa.lris.schedule.database.patterns.CompositePatternForTask;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "composite_task")
public class CompositeTask extends EntityPrototype implements CompositePatternForTask {
    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private Integer time;
    @Column(name = "score")
    private Integer score;
    @OneToMany
    private List<CompositePatternForTask> children;

    public CompositeTask() {
        this.name = "";
        this.time = 0;
        this.score = 0;
        children = new ArrayList<>();
    }

    public CompositeTask(String name, List<CompositeTask> compositeTaskList) {
        this.name = name;
        if (compositeTaskList == null || compositeTaskList.isEmpty()) {
            this.score = 0;
            this.time = 0;
        } else {
            this.score = compositeTaskList.stream().mapToInt(CompositePatternForTask::getScore).sum();
            this.time = compositeTaskList.stream().mapToInt(CompositePatternForTask::getTime).sum();
        }
    }

    public List<CompositePatternForTask> getChildren() {
        return children;
    }

    public void setChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) {
            this.children = compositePatternForTaskList;
            this.score = compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getScore).sum();
            this.time = compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getTime).sum();
        }
    }

    public void addChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) this.children.addAll(compositePatternForTaskList);
        this.score += compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getScore).sum();
        this.time += compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getTime).sum();
    }

    public void addChild(CompositePatternForTask compositePatternForTask) {
        if (compositePatternForTask != null) this.children.add(compositePatternForTask);
        this.score += compositePatternForTask.getScore();
        this.time += compositePatternForTask.getTime();
    }

    public void removeChild(CompositePatternForTask compositePatternForTask) {
        if (compositePatternForTask != null) this.children.remove(compositePatternForTask);
        this.score -= compositePatternForTask.getScore();
        this.time -= compositePatternForTask.getTime();
    }

    public void removeChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) this.children.removeAll(compositePatternForTaskList);
        this.score -= compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getScore).sum();
        this.time -= compositePatternForTaskList.stream().mapToInt(CompositePatternForTask::getTime).sum();
    }

    public void removeAllChildren() {
        this.children.clear();
        this.score = 0;
        this.time = 0;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
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

    }

    @Override
    public Integer getTime() {
        return time;
    }

    @Override
    public void setTime(Integer time) {

    }

    @Override
    public String toString() {
        return "CompositeTask{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeTask that = (CompositeTask) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(time, that.time) &&
                Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, time, score, children);
    }
}
