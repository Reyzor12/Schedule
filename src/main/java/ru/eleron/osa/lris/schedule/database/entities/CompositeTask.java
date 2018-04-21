package ru.eleron.osa.lris.schedule.database.entities;

import ru.eleron.osa.lris.schedule.database.patterns.CompositePatternForTask;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
            this.score = compositeTaskList.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
            this.time = compositeTaskList.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
        }
    }

    public List<CompositePatternForTask> getChildren() {
        return children;
    }

    public void setChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) this.children = compositePatternForTaskList;
    }

    public void addChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) this.children.addAll(compositePatternForTaskList);
    }

    public void addChild(CompositePatternForTask compositePatternForTask) {
        if (compositePatternForTask != null) this.children.add(compositePatternForTask);
    }

    public void removeChild(CompositePatternForTask compositePatternForTask) {
        if (compositePatternForTask != null) this.children.remove(compositePatternForTask);
    }

    public void removeChildren(List<CompositePatternForTask> compositePatternForTaskList) {
        if (compositePatternForTaskList != null) this.children.removeAll(compositePatternForTaskList);
    }

    public void removeAllChildren() {
        this.children.clear();
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
        if (children == null || children.isEmpty()) {
            this.score = 0;
            this.time = 0;
        } else {
            this.score = children.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
            this.time = children.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
        }
    }

    @Override
    public Integer getTime() {
        return time;
    }

    @Override
    public void setTime(Integer time) {
        if (compositeTaskList == null || compositeTaskList.isEmpty()) {
            this.score = 0;
            this.time = 0;
        } else {
            this.score = compositeTaskList.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
            this.time = compositeTaskList.stream().mapToInt(compositeTask -> compositeTask.getScore()).sum();
        }
    }
}
