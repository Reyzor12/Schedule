package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.TypeOfCompositeTask;

/**
 * Cache for composite task with value of parameter equals {@link TypeOfCompositeTask#DAY}
 * it's realized {@link TaskCache} and is used in cache pool at {@link ObservableData}
 * @author reyzor
 * @version 1.0
 * @since 17.05.2018
 */

@Component("dayTaskTemplateCache")
public class DayTaskTemplateCache implements TaskCache<CompositeTask> {

    private ObservableList<CompositeTask> cache;
    private Boolean isInitCache;

    @Autowired
    private CompositeTaskDao compositeTaskDao;

    public DayTaskTemplateCache()
    {
        cache = FXCollections.observableArrayList();
        isInitCache = false;
    }

    @Override
    public ObservableList<CompositeTask> getObservableList()
    {
        if (!isInitCache) refreshData();
        return cache;
    }

    @Override
    public void refreshData()
    {
        cache.setAll(compositeTaskDao.getByType(TypeOfCompositeTask.DAY));
        isInitCache = true;
    }
}
