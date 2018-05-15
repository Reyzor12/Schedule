package ru.eleron.osa.lris.schedule.utils.cache;

public enum ObservableDataMarkers {
    TASK_TEMPLATES("TABLE_TASK_TEMPLATE");

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
