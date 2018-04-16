package ru.eleron.osa.lris.schedule.database.entities.base;

import java.util.Collection;
import java.util.List;

public interface BaseTask {

    Long getId();
    void setId(Long id);
    String getName();
    void setName(String newName);
    Integer getScore();
    void setScore(Integer score);
    Integer getTimeInMinutes();
    void setTimeInMinutes();
    List<BaseTask> getChildrens();
    void setChildrens(Collection<BaseTask> baseTaskCollection);
    void addChildrens(Collection<BaseTask> baseTaskCollection);
    void addChildrens(BaseTask baseTask);
    void removeChildrens(Collection<BaseTask> baseTaskCollection);
    void removeChildrens(BaseTask baseTask);
}
