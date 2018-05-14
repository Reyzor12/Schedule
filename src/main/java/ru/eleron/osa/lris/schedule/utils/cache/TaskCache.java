package ru.eleron.osa.lris.schedule.utils.cache;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache for composite task
 * @author Reyzor
 * @version 1.0
 * @since 14.05.2018
 */

public abstract class TaskCache<T>
{
    protected List<T> cache;

    protected TaskCache()
    {
        cache = new ArrayList<>();
    }

    public List<T> getCache()
    {
        if (cache.isEmpty()) refresh();
        return cache;
    }

    public abstract void refresh();
}
