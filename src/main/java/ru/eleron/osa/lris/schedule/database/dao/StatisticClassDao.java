package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTaskProxyCompositeKey;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;

import java.util.Date;
import java.util.List;

public interface StatisticClassDao extends CrudRepository<StatisticClass, CompositeTaskProxyCompositeKey>
{
    @Query("from StatisticClass where compositeKey.proxyCompositeTask = :proxyCompositeKey ")
    List<StatisticClass> performedTask(@Param("proxyCompositeKey") ProxyCompositeTask proxyCompositeKey);
    @Query("from StatisticClass statistic where statistic.compositeKey.proxyCompositeTask.date between :start and :end")
    List<StatisticClass> getStatisticBetweenDate(@Param("start")Date start,@Param("end") Date end);
}
