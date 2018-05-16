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

    //cache pool with all observable cache
    private Map<String, TaskCache> data;
    private Boolean isInit;
    private ObservableData()
    {
        data = new HashMap<>();
        isInit = false;
    }

    /**
     * Register new cache in cache pool
     * use String as key for cache
     * @param dataObject - cache object {@link TaskCache}
     * @param templateName - string key for cache in cache pool
     * */

    public void registerTableData(String templateName, TaskCache dataObject)
    {
        if (templateName != null && !templateName.trim().equals("") && dataObject != null) data.put(templateName, dataObject);
    }

    /**
     * If cache pool is not initialize then init pool, than if key is present in pool get cache {@link TaskCache}
     * and receive observable data {@link TaskCache#getObservableList()} else return null
     * @param templateName - string key for cache in cache pool
     * */

    public ObservableList getData(String templateName)
    {
        if (!isInit) initData();
        TaskCache result = data.getOrDefault(templateName, null);
        return  result == null ? null : result.getObservableList();
    }

    /**
     * Init data in cache pool
     * */

    private void initData()
    {
        registerTableData("TABLE_TASK_TEMPLATE", taskTemplateCache);
        isInit = true;
    }
}
