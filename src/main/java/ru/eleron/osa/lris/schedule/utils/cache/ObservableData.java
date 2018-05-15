package ru.eleron.osa.lris.schedule.utils.cache;

import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

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
    @Autowired
    @Qualifier("taskTemplateCache")
    private TaskCache<CompositeTask> taskTemplateCache;

    private Map<String, TaskCache> data;
    private ObservableData()
    {
        data = new HashMap<>();
    }


    public void registerTableData(String templateName, TaskCache dataObject)
    {
        if (templateName != null && !templateName.trim().equals("") && dataObject != null) data.put(templateName, dataObject);
    }

    public ObservableList getData(String templateName)
    {
        if (data.isEmpty()) initData();
        TaskCache result = data.getOrDefault(templateName, null);
        return  result == null ? null : result.getObservableList();
    }

    private void initData()
    {
        registerTableData("TABLE_TASK_TEMPLATE", taskTemplateCache);
    }
}
