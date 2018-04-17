package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eleron.osa.lris.schedule.database.entities.Task;

public interface TaskDao extends JpaRepository<Task, Long> {
}
