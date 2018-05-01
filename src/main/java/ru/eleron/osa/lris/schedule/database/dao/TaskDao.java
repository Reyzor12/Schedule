package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

public interface TaskDao extends JpaRepository<CompositeTask, Long> {
    CompositeTask getByName(String name);
}
