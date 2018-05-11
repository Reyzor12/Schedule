package ru.eleron.osa.lris.schedule.utils.storage;

/**
 * Class contains constants for all elements in controllers such as placeholders, labels, tooltips and so on
 * @author reyzor
 * @version 1.0
 * @since 04.05.2018
 * */

public enum ConstantsForElements
{
    /**
     * Placeholder for tables
     * */

    EMPTY_CURRENT_TASK_TABLE("Задачи на сегодня отсутствуют"),
    EMPTY_TASK_TEMPLATES("Шаблоны задач отсутствуют"),
    EMPTY_COMPOSITE_TASK("Задачи для шаблона отсутствуют"),

    /**
     * Path to arrow images
     * */

    ARROW_LEFT("images/icons/left.png"),
    ARROW_RIGHT("images/icons/right.png"),
    ARROW_LEFT_BLACK("images/icons/left-black.png"),
    ARROW_RIGHT_BLACK("images/icons/right-black.png"),

    /**
     * Path to images for spinner
     * */

    DEVISION("images/number/devision.png"),
    NUMBER_1("images/number/1.png"),
    NUMBER_2("images/number/2.png"),
    NUMBER_3("images/number/3.png"),
    NUMBER_4("images/number/4.png"),
    NUMBER_5("images/number/5.png"),
    NUMBER_6("images/number/6.png"),
    NUMBER_7("images/number/7.png"),
    NUMBER_8("images/number/8.png"),
    NUMBER_9("images/number/9.png"),
    NUMBER_0("images/number/0.png");

    private String message;

    ConstantsForElements(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
