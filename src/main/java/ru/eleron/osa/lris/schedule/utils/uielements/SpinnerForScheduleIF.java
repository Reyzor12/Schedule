package ru.eleron.osa.lris.schedule.utils.uielements;

import java.util.List;

public interface SpinnerForScheduleIF<T> {
    List<T> next();
    List<T> previous();
    boolean hasNext();
    boolean hasPrevious();
    List<T> getCurrentValue();
}
