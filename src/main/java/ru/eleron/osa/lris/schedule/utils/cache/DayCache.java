package ru.eleron.osa.lris.schedule.utils.cache;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache of User current day
 * list of cache data:
 * time
 * score
 * task template
 * current date
 * marks for tasks
 *
 * @author reyzor
 * @version 1.0
 * @since 18.05.2018
 */

@Component
public class DayCache {

    private CompositeTask templateScheduleForDay;
    private ProxyCompositeTask scheduleForDay;
    private List<StatisticClass> marksForTask;

    public DayCache()
    {
        this.marksForTask = new ArrayList<>();
    }

    public CompositeTask getTemplateScheduleForDay() {
        return templateScheduleForDay;
    }

    public void setTemplateScheduleForDay(CompositeTask templateScheduleForDay) {
        this.templateScheduleForDay = templateScheduleForDay;
    }

    public ProxyCompositeTask getScheduleForDay() {
        return scheduleForDay;
    }

    public void setScheduleForDay(ProxyCompositeTask scheduleForDay) {
        this.scheduleForDay = scheduleForDay;
    }

    public List<StatisticClass> getMarksForTask() {
        return marksForTask;
    }

    public void setMarksForTask(List<StatisticClass> marksForTask) {
        this.marksForTask = marksForTask;
    }

    @Override
    public String toString() {
        return "DayCache{" +
                "templateScheduleForDay=" + templateScheduleForDay +
                ", scheduleForDay=" + scheduleForDay +
                ", marksForTask=" + marksForTask +
                '}';
    }
}
