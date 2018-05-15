package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

import java.util.List;

public interface CompositeTaskDao extends JpaRepository<CompositeTask, Long>
{
    CompositeTask getByName(String name);
    List<CompositeTask> getByType(Integer type);
}
