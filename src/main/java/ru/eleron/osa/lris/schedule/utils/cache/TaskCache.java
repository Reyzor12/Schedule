package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.ObservableList;

/**
 * Cache for composite task, it's use in cache pool
 * @see ObservableData
 * @author Reyzor
 * @version 1.0
 * @since 14.05.2018
 */

public interface TaskCache<T>
{
    /**
     * Get objects from DB and wrap it into observable list
     * */
    ObservableList<T> getObservableList();

    /**
     * Get objects from DB
     * */

    void refreshData();
}
