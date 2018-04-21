package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eleron.osa.lris.schedule.database.entities.Task;
import ru.eleron.osa.lris.schedule.database.patterns.CompositePatternForTask;

public interface TaskDao extends JpaRepository<CompositePatternForTask, Long> {
}
