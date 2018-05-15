package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.ObservableList;
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

public interface TaskCache<T>
{
    ObservableList<T> getObservableList();
    void refreshData();
}
