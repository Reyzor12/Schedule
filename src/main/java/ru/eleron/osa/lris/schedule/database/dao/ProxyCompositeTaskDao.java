package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;

import java.util.Date;
import java.util.List;

public interface ProxyCompositeTaskDao extends CrudRepository<ProxyCompositeTask, Long>
{
    ProxyCompositeTask findByName(String name);
    @Query("FROM ProxyCompositeTask WHERE YEAR(date) = :year AND MONTH(date) = :month AND DAY(date) = :day")
    ProxyCompositeTask findByDate(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);
    @Query("FROM ProxyCompositeTask WHERE date between :start and :end and compositeTask.type=1")
    List<ProxyCompositeTask> findFromDateToDate(@Param("start") Date start,@Param("end") Date end);
}
