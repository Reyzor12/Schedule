package ru.eleron.osa.lris.schedule.database.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.schedule.configurations.JpaConfigurations;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigurations.class})
@Transactional
public class TaskDaoTest {

    @Resource
    private TaskDao taskDao;

    @Autowired
    //@Qualifier("sessionFactory")
    private EntityManager sessionFactory;

    private CompositeTask compositeTask;
    private CompositeTask compositeTask1;

    @Before
    public void initData() {
        compositeTask = new CompositeTask();
        compositeTask.setName("Name");
        compositeTask.setScore(123);
        compositeTask.setTime(321);
        compositeTask1 = new CompositeTask();
    }

    @Test
    public void addToDb() {
        assertTrue(compositeTask != null);
        taskDao.save(compositeTask);
        CompositeTask task2 = (CompositeTask) taskDao.getOne(2l);
        System.out.println(taskDao.findAll());
        assertTrue(task2 != null);
        assertTrue(compositeTask.equals(task2));
        assertTrue(task2.getName().equals(compositeTask.getName()));
    }

    @Test
    public void createEmptyAndSaveCompositeTask() {
        CompositeTask compositeTask = new CompositeTask();
        
    }
}