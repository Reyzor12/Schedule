package ru.eleron.osa.lris.schedule.database.entities;

import ru.eleron.osa.lris.schedule.database.entities.base.BaseTask;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "task")
public class Task extends EntityPrototype implements BaseTask{
    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private Integer score;
    @Column(name = "time")
    private Integer time;
    @OneToMany
    private List<BaseTask> baseTaskList;

    public String getName() {
        return null;
    }

    public void setName(String newName) {

    }

    public Integer getScore() {
        return null;
    }

    public void setScore(Integer score) {

    }

    public Integer getTimeInMinutes() {
        return null;
    }

    public void setTimeInMinutes() {

    }

    public List<BaseTask> getChildrens() {
        return null;
    }

    public void setChildrens(Collection<BaseTask> baseTaskCollection) {

    }

    public void addChildrens(Collection<BaseTask> baseTaskCollection) {

    }

    public void addChildrens(BaseTask baseTask) {

    }

    public void removeChildrens(Collection<BaseTask> baseTaskCollection) {

    }

    public void removeChildrens(BaseTask baseTask) {

    }
}
