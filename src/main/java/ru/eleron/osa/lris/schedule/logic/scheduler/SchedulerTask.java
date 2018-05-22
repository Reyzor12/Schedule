package ru.eleron.osa.lris.schedule.logic.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsSittings;

/**
 * Start schedule for list of tasks
 * @author Reyzor
 * @version 1.0
 * @since 20.05.2018
 */

@Component
public class SchedulerTask
{
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler()
    {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(ConstantsSittings.SCHEDULE_POOL_SIZE.getIntegerConstant());
        threadPoolTaskScheduler.setThreadNamePrefix(ConstantsSittings.SCHEDULE_POOL_NAME_PREFIX.getStringConstant());
        return threadPoolTaskScheduler;
    }
}
