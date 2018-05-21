package ru.eleron.osa.lris.schedule.logic.scheduler;

import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

/**
 * Start schedule for list of tasks
 * @author Reyzor
 * @version 1.0
 * @since 20.05.2018
 */

@Component
public class SchedulerTask
{
    private String start;

    public SchedulerTask()
    {

    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Scheduled(cron = "10 * * * * *")
    public void runSchedule()
    {
        System.out.println("Composite Task = " + 1);
    }
}
