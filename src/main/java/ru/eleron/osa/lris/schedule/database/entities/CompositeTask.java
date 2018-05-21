package ru.eleron.osa.lris.schedule.database.entities;

import ru.eleron.osa.lris.schedule.database.patterns.ClonnableObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Entity like a task (template). Main object of application
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

@Entity
@Table(name = "composite_task")
public class CompositeTask extends EntityPrototype implements ClonnableObject<CompositeTask>
{

    public static final Integer DEFAULT_SCORE = 0;
    public static final Integer DEFAULT_TIME = 0;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private Integer time;
    @Column(name = "score")
    private Integer score;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="type")
    private TypeOfCompositeTask type;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<CompositeTask> children;

    public CompositeTask()
    {
        this.name = "";
        this.time = DEFAULT_TIME;
        this.score = DEFAULT_SCORE;
        this.type = TypeOfCompositeTask.TASK;
        children = new ArrayList<>();
    }

    public CompositeTask(String name, TypeOfCompositeTask task, List<CompositeTask> compositeTaskList)
    {
        this.name = name;
        if (compositeTaskList == null || compositeTaskList.isEmpty())
        {
            this.type = TypeOfCompositeTask.TASK;
            this.score = DEFAULT_SCORE;
            this.time = DEFAULT_TIME;
        } else {
            this.score = compositeTaskList.stream().mapToInt(CompositeTask::getScore).sum();
            this.time = compositeTaskList.stream().mapToInt(CompositeTask::getTime).sum();
            this.type = task;
            this.children = compositeTaskList;
        }
    }

    public CompositeTask(CompositeTask compositeTask)
    {
        this.name = compositeTask.getName();
        this.score = compositeTask.getScore();
        this.time = compositeTask.getTime();
        this.type = compositeTask.getType();
        final List<CompositeTask> anotherChildren = compositeTask.getChildren();
        this.children = anotherChildren == null ? null : (anotherChildren.isEmpty() ? new ArrayList<>() : anotherChildren.stream().map(compositeTask1 -> compositeTask1.clone()).collect(Collectors.toList()));
    }

    public boolean isTask()
    {
        return type.equals(TypeOfCompositeTask.TASK) || type.equals(TypeOfCompositeTask.TASK_IN_DAY);
    }

    public List<CompositeTask> getChildren()
    {
        return children;
    }

    public void setChildren(List<CompositeTask> compositePatternForTaskList)
    {
        if (!isTask())
        {
            if (compositePatternForTaskList != null) {
                this.children = compositePatternForTaskList;
                this.score = compositePatternForTaskList.stream().mapToInt(CompositeTask::getScore).sum();
                this.time = compositePatternForTaskList.stream().mapToInt(CompositeTask::getTime).sum();
            }
        }
    }

    public void addChildren(List<CompositeTask> compositePatternForTaskList)
    {
        if (!isTask())
        {
            if (compositePatternForTaskList != null) this.children.addAll(compositePatternForTaskList);
            this.score += compositePatternForTaskList.stream().mapToInt(CompositeTask::getScore).sum();
            this.time += compositePatternForTaskList.stream().mapToInt(CompositeTask::getTime).sum();
        }
    }

    public void addChild(CompositeTask compositePatternForTask)
    {
        if (!isTask())
        {
            if (compositePatternForTask != null) this.children.add(compositePatternForTask);
            this.score += compositePatternForTask.getScore();
            this.time += compositePatternForTask.getTime();
        }
    }

    public void removeChild(CompositeTask compositePatternForTask)
    {
        if (!isTask())
        {
            if (compositePatternForTask != null) this.children.remove(compositePatternForTask);
            this.score -= compositePatternForTask.getScore();
            this.time -= compositePatternForTask.getTime();
        }
    }

    public void removeChildren(List<CompositeTask> compositePatternForTaskList)
    {
        if(!isTask())
        {
            if (compositePatternForTaskList != null) this.children.removeAll(compositePatternForTaskList);
            this.score -= compositePatternForTaskList.stream().mapToInt(CompositeTask::getScore).sum();
            this.time -= compositePatternForTaskList.stream().mapToInt(CompositeTask::getTime).sum();
        }
    }

    public void removeAllChildren()
    {
        if (isTask())
        {
            this.children.clear();
            this.score = DEFAULT_SCORE;
            this.time = DEFAULT_TIME;
        }
    }

    public boolean hasChildren()
    {
        if (isTask()) return false;
        return !this.children.isEmpty();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        if (isTask())
        {
            this.score = score;
        }
    }

    public Integer getTime()
    {
        return time;
    }

    public void setTime(Integer time)
    {
        if (isTask())
        {
            this.time = time;
        }
    }

    public TypeOfCompositeTask getType() {
        return type;
    }

    public void setType(TypeOfCompositeTask type) {
        this.type = type;
    }

    public String toString()
    {
        return "CompositeTask{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", score=" + score +
                ", type=" + type.getType() +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeTask that = (CompositeTask) o;
        return  Objects.equals(getId(), that.getId()) &&
                Objects.equals(name, that.name) &&
                Objects.equals(time, that.time) &&
                Objects.equals(type, that.type) &&
                Objects.equals(score, that.score);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(name, time, type, score);
    }

    @Override
    public CompositeTask clone()
    {
        return new CompositeTask(this);
    }
}
