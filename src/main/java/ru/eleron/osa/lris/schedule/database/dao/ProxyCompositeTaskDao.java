package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;

public interface ProxyCompositeTaskDao extends CrudRepository<ProxyCompositeTask, Long>
{
    ProxyCompositeTask findByName(String name);
    @Query("FROM ProxyCompositeTask WHERE YEAR(date) = :year AND MONTH(date) = :month AND DAY(date) = :day")
    ProxyCompositeTask findByDate(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);
}
