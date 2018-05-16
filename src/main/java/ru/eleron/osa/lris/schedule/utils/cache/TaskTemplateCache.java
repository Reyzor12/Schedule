package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.TypeOfCompositeTask;

/**
 * Cache for composite task, it's realisation of {@link TaskCache}, and use in cache pool {@link ObservableData}
 * @author Reyzor
 * @version 1.0
 * @since 14.05.2018
 */

@Component("taskTemplateCache")
public class TaskTemplateCache implements TaskCache<CompositeTask>
{
    private ObservableList<CompositeTask> cache;
    private Boolean isInitCache;

    @Autowired
    private CompositeTaskDao compositeTaskDao;

    private TaskTemplateCache()
    {
        cache = FXCollections.observableArrayList();
        isInitCache = false;
    }

    @Override
    public ObservableList<CompositeTask> getObservableList() {
        if (!isInitCache) refreshData();
        return cache;
    }

    @Override
    public void refreshData() {
        cache.setAll(compositeTaskDao.getByType(TypeOfCompositeTask.TASK));
        isInitCache = true;
    }
}
