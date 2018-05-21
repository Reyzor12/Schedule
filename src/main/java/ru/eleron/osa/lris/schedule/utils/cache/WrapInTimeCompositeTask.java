package ru.eleron.osa.lris.schedule.utils.cache;

import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;

import java.time.LocalTime;

/**
 * Wrap composite task with time start and end and mark
 * @author Reyzor
 * @version 1.0
 * @since 20.05.2018
 */

public class WrapInTimeCompositeTask {

    private CompositeTask compositeTask;
    private LocalTime start;
    private LocalTime end;
    private MarkForTask mark;

    public WrapInTimeCompositeTask(CompositeTask compositeTask, LocalTime start)
    {
        this.compositeTask = compositeTask;
        this.start = start;
        this.end = start.plusMinutes(compositeTask.getTime());
    }

    public CompositeTask getCompositeTask() {
        return compositeTask;
    }

    public void setCompositeTask(CompositeTask compositeTask) {
        this.compositeTask = compositeTask;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public MarkForTask getMark() {
        return mark;
    }

    public void setMark(MarkForTask mark) {
        this.mark = mark;
    }
}
