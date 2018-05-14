package ru.eleron.osa.lris.schedule.utils.cache;

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

@Component
public class CompositeTaskCache extends TaskCache<CompositeTask>
{
    @Autowired
    private CompositeTaskDao compositeTaskDao;

    private CompositeTaskCache()
    {
        super();
    }

    @Override
    public void refresh()
    {
        super.cache.clear();
        super.cache.addAll(compositeTaskDao.findAll());
    }
}
