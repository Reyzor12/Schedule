package ru.eleron.osa.lris.schedule.logic.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.MainApp;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.cache.WrapInTimeCompositeTask;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Runnable class, description of schedule for user
 * @author reyzor
 * @version 1.0
 * @since 21.05.2018
 */

@Component
public class ScheduleForUser implements Runnable
{

    private final static Logger logger = LogManager.getLogger(ScheduleForUser.class);

    @Autowired
    private DayCache dayCache;
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    private LocalTime localTime;
    private List<CompositeTask> doneCompositeTask;

    @Override
    public void run()
    {
        if(dayCache == null)
        {
            logger.warn("dayCache don't load");
            return;
        } else if (!dayCache.isDefine())
        {
            logger.warn("dayCache don't define");
            return;
        } else
        {
            localTime = LocalTime.now();
            doneCompositeTask = dayCache.getMarksForTask().stream().map(statisticClass -> statisticClass.getCompositeKey().getCompositeTask()).collect(Collectors.toList());
            registerSchedule();
        }
    }

    /**
     * Create schedule for day
     * register all tasks
     * */

    private void registerSchedule()
    {
        dayCache
                .getTaskWithTime()
                .entrySet()
                .stream()
                .forEach(entry ->  registerTask(entry.getKey(), entry.getValue()));
    }

    /**
     * register one task in schedule
     * */
    private void registerTask(CompositeTask compositeTask, WrapInTimeCompositeTask timeCompositeTask)
    {
        System.out.println("schedule for " + compositeTask + " registered");
        if (doneCompositeTask.contains(compositeTask)) return;
        System.out.println("schedule for " + compositeTask + " registered well");
        //scheduler.schedule(new TaskLoad(compositeTask), Date.from(timeCompositeTask.getStart().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
        localTime = localTime.plusSeconds(5);
        TaskLoad task = (TaskLoad) MainApp.APPLICATION_CONTEXT.getBean("taskLoad");
        task.setCompositeTask(compositeTask);
        scheduler.schedule(task, Date.from(localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
    }
}
