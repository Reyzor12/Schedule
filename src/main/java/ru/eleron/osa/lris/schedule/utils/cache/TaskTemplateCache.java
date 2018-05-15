package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

/**
 * Cache for composite task
 * @author Reyzor
 * @version 1.0
 * @since 14.05.2018
 */

@Component("taskTemplateCache")
public class TaskTemplateCache implements TaskCache<CompositeTask>
{
    private ObservableList<CompositeTask> cache;

    @Autowired
    private CompositeTaskDao compositeTaskDao;

    private TaskTemplateCache()
    {
        cache = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<CompositeTask> getObservableList() {
        return cache;
    }

    @Override
    public void refreshData() {
        cache.setAll(compositeTaskDao.findAll());
    }
}
