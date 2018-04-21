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
import ru.eleron.osa.lris.schedule.database.entities.Task;
import ru.eleron.osa.lris.schedule.database.patterns.CompositePatternForTask;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import java.sql.Connection;
import java.sql.DriverManager;

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

    private Task task1;

    @Before
    public void initData() {
        task1 = new Task();
        task1.setName("Name");
        task1.setScore(123);
        task1.setTime(321);
    }

    @Test
    public void addToDb() {
        assertTrue(task1 != null);
        taskDao.save(task1);
        Task task2 = (Task) taskDao.getOne(2l);
        System.out.println(taskDao.findAll());
        assertTrue(task2 != null);
        assertTrue(task1.equals(task2));
        assertTrue(task2.getName().equals(task1.getName()));
    }

    @Test
    public void createEmptyAndSaveCompositeTask() {
        CompositePatternForTask compositeTask = new CompositeTask();
        
    }
}