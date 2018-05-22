package ru.eleron.osa.lris.schedule.utils.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.ProxyCompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.dao.StatisticClassDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
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
    private Boolean isTaskInSchedule;
    private List<ProxyCompositeTask> weekProxyCompositeTasks;
    private List<ProxyCompositeTask> monthProxyCompositeTasks;
    private List<StatisticClass> weekStatistic;
    private List<StatisticClass> monthStatistic;

    @Autowired
    private StatisticClassDao statisticClassDao;

    public DayCache()
    {
        this.marksForTask = new ArrayList<>();
        this.isTaskInSchedule = false;
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

    public Boolean getTaskInSchedule() {
        return isTaskInSchedule;
    }

    public void setTaskInSchedule(Boolean taskInSchedule) {
        isTaskInSchedule = taskInSchedule;
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

    public List<StatisticClass> getWeekStatistic(LocalDateTime endDate)
    {
        if (hasWeekStatistic()) return weekStatistic;
        final LocalDateTime startDate = endDate.minusWeeks(1);
        weekStatistic = statisticClassDao.getStatisticBetweenDate
                (
                        Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant())
                );
        return weekStatistic;
    }
    public List<StatisticClass> getMonthStatistic(LocalDateTime endDate)
    {
        if (hasMonthStatistic()) return monthStatistic;
        final LocalDateTime startDate = endDate.minusMonths(1);
        monthStatistic = statisticClassDao.getStatisticBetweenDate
                (
                        Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant())
                );
        return monthStatistic;
    }

    public boolean hasWeekStatistic()
    {
        return weekStatistic != null;
    }
    public boolean hasMonthStatistic()
    {
        return monthStatistic != null;
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
