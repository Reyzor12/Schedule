package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Observable data for all tables in application
 * @author Reyzor
 * @version 1.0
 * @since 14.05.2018
 */

@Component
public class ObservableData
{
    private Map<String, ObservableList> data;
    private ObservableData()
    {
        data = new HashMap<>();
    }

    enum Tables
    {
        TABLE_TASK_TEMPLATE("TABLE_TASK_TEMPLATE");

        private String title;
        Tables(String title)
        {
            this.title = title;
        }
    }
}
