package ru.eleron.osa.lris.schedule.utils.cache;

/**
 * Class only for cache pool {@link ObservableData}
 * it's keys for cache
 * */

public enum ObservableDataMarkers {
    TASK_TEMPLATES("TABLE_TASK_TEMPLATE"),
    DAY_TASK_TEMPLATES("DAY_TASK_TEMPLATES");

    private String value;

    ObservableDataMarkers(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
