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
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Integer getScore() {
        return null;
    }

    @Override
    public void setScore(Integer score) {

    }

    @Override
    public Integer getTime() {
        return null;
    }

    @Override
    public void setTime(Integer time) {

    }
}
