package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;

public interface ProxyCompositeTaskDao extends CrudRepository<ProxyCompositeTask, Long>
{
    ProxyCompositeTask findByName(String name);
}
