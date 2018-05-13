package ru.eleron.osa.lris.schedule.database.entities;

/**
 *
 * Class for one of the types of composite task:
 * task, day, week, month
 * @author Reyzor
 * @version 1.0
 * @since 13.05.2018
 * */


public enum TypeOfCompositeTask
{
    TASK(0),
    DAY(1),
    WEEK(2),
    MONTH(3);

    private Integer info;

    TypeOfCompositeTask(Integer info)
    {
        this.info = info;
    }

    public Integer getType()
    {
        return info;
    }
}


