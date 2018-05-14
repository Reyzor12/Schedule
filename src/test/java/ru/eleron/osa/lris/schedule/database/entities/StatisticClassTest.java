package ru.eleron.osa.lris.schedule.database.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.schedule.configurations.JpaConfigurations;
import ru.eleron.osa.lris.schedule.database.dao.ProxyCompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.dao.StatisticClassDao;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigurations.class})
@Transactional
public class StatisticClassTest {

    @Autowired
    private CompositeTaskDao compositeTaskDao;
    @Autowired
    private ProxyCompositeTaskDao proxyCompositeTaskDao;
    @Autowired
    private StatisticClassDao statisticClassDao;

    private CompositeTask task;
    private ProxyCompositeTask proxyTask;

    @Before
    public void initData() {
        task = compositeTaskDao.getOne(1l);
        assertTrue(task != null);
        proxyTask = proxyCompositeTaskDao.findByName("hello world");
        assertTrue(proxyTask != null);
    }

    @Test
    public void testForSaveStatisticClassInDb() {
        StatisticClass statisticClass = new StatisticClass(task, proxyTask, MarkForTask.MARK_A);
        statisticClassDao.save(statisticClass);
        statisticClassDao.delete(statisticClass);
    }

    @Test
    public void testForGetStatisticClassFromDb() {
        StatisticClass statisticClass = new StatisticClass(task, proxyTask, MarkForTask.MARK_A);
        statisticClassDao.save(statisticClass);
        StatisticClass statisticClassAnother = statisticClassDao.findOne(new CompositeTaskProxyCompositeKey(task, proxyTask));
        assertTrue(statisticClassAnother != null);
    }
}