package ru.eleron.osa.lris.schedule.database.dao;

import org.springframework.data.repository.CrudRepository;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTaskProxyCompositeKey;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;

public interface StatisticClassDao extends CrudRepository<StatisticClass, CompositeTaskProxyCompositeKey> {
}
