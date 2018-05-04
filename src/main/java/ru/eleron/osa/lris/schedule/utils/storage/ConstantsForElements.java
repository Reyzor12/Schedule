package ru.eleron.osa.lris.schedule.utils.storage;

/**
 * Class contains constants for all elements in controllers such as placeholders, labels, tooltips and so on
 * @author reyzor
 * @version 1.0
 * @since 04.05.2018
 * */

public enum ConstantsForElements {

    EMPTY_CURRENT_TASK_TABLE("Задачи на сегодня отсутствуют");

    private String message;

    ConstantsForElements(String message) {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
