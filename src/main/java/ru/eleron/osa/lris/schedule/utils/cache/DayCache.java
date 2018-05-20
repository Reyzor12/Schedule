package ru.eleron.osa.lris.schedule.utils.cache;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
    private Map<CompositeTask, WrapInTimeCompositeTask> taskWithTime;

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

    public void setScheduleForDay(ProxyCompositeTask scheduleForDay)
    {
        this.scheduleForDay = scheduleForDay;
        taskWithTime = new HashMap<>();
        AtomicReference<LocalTime> localTime = new AtomicReference<>(scheduleForDay.getDate().toLocalTime());
        scheduleForDay.getCompositeTask().getChildren().stream().forEach(compositeTask -> {
            taskWithTime.put(compositeTask, new WrapInTimeCompositeTask(compositeTask,localTime.get()));
            localTime.set(localTime.get().plusMinutes(compositeTask.getTime()));
        });
    }

    public List<StatisticClass> getMarksForTask() {
        return marksForTask;
    }

    public void setMarksForTask(List<StatisticClass> marksForTask) {
        this.marksForTask = marksForTask;
    }

    public boolean isDefine()
    {
        return (templateScheduleForDay == null || scheduleForDay == null) ? false : true;
    }

    public Map<CompositeTask, WrapInTimeCompositeTask> getTaskWithTime() {
        return taskWithTime;
    }

    public void setTaskWithTime(Map<CompositeTask, WrapInTimeCompositeTask> taskWithTime) {
        this.taskWithTime = taskWithTime;
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
