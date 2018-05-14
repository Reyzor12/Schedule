package ru.eleron.osa.lris.schedule.database.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.schedule.configurations.JpaConfigurations;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.TypeOfCompositeTask;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigurations.class})
@Transactional
public class CompositeTaskDaoTest {

    @Resource
    private CompositeTaskDao compositeTaskDao;
    @Resource
    private ProxyCompositeTaskDao proxyCompositeTaskDao;

    @Autowired
    private EntityManager sessionFactory;

    private CompositeTask compositeTask;
    private CompositeTask leaf;
    private CompositeTask anotherLeaf;
    private ProxyCompositeTask proxyCompositeTask;

    @Before
    public void initData() {
        compositeTask = new CompositeTask();
        compositeTask.setName("Name");
        compositeTask.setScore(123);
        compositeTask.setTime(321);
        leaf = new CompositeTask("List", TypeOfCompositeTask.DAY, null);
        leaf.setScore(100);
        leaf.setTime(200);
        compositeTask.addChild(leaf);
        anotherLeaf = new CompositeTask("Another Leaf", TypeOfCompositeTask.DAY, null);
        anotherLeaf.setScore(55);
        anotherLeaf.setTime(22);
        compositeTask.addChild(anotherLeaf);
        proxyCompositeTask = new ProxyCompositeTask(new Date(), compositeTask, "Proxy 2");
    }

    @Test
    public void addToDb() {
        assertTrue(compositeTask != null);
        compositeTaskDao.save(leaf);
        compositeTaskDao.save(anotherLeaf);
        compositeTaskDao.save(compositeTask);
        CompositeTask task2 = (CompositeTask) compositeTaskDao.getByName("Name");
        System.out.println(compositeTaskDao.findAll());
        assertTrue(task2 != null);
        assertTrue(compositeTask.equals(task2));
        assertTrue(task2.getName().equals(compositeTask.getName()));

    }

    @Test
    public void testCloneForCompositeTask() {
        CompositeTask cloneCompositeTask = compositeTask.clone();
        System.out.println("cloneCompositeTask = " + cloneCompositeTask);
        System.out.println("cloneCompositeTask.getChildren() = " + cloneCompositeTask.getChildren());
        System.out.println("compositeTask = " + compositeTask);
        System.out.println("compositeTask.getChildren() = " + compositeTask.getChildren());
        assertTrue(cloneCompositeTask.equals(compositeTask));
        assertTrue(cloneCompositeTask.getChildren().equals(compositeTask.getChildren()));
    }
    @Test
    public void testIncludeElements() {
        CompositeTask task1 = new CompositeTask();
        CompositeTask task2 = new CompositeTask();
        task1.addChild(leaf);
        task1.addChild(anotherLeaf);
        task2.addChild(anotherLeaf);
        task2.addChild(leaf);
        System.out.println(task1.getChildren());
        System.out.println(task2.getChildren());
        Assert.assertFalse(task1.getChildren().equals(task2.getChildren()));
    }

    @Test
    public void saveAndDeleteProxyCompositeTask() {
        proxyCompositeTaskDao.save(proxyCompositeTask);
        System.out.println("proxyCompositeTask = " + proxyCompositeTask);
        System.out.println("all = " + proxyCompositeTaskDao.findAll());
        ProxyCompositeTask anotherProxyCompositeTask = proxyCompositeTaskDao.findByName("Proxy 2");
        assertTrue(anotherProxyCompositeTask != null);
        assertTrue(anotherProxyCompositeTask.getCompositeTask().equals(compositeTask));
        //proxyCompositeTaskDao.delete(2l);
        //assertTrue(StreamSupport.stream(Spliterators.spliteratorUnknownSize(proxyCompositeTaskDao.findAll().iterator(), Spliterator.ORDERED), false).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void crudModelForProxyTest() {
        ProxyCompositeTask proxyCompositeTask1 = proxyCompositeTaskDao.findByName("hello world");
        assertTrue(proxyCompositeTask1 != null);
        assertTrue(proxyCompositeTask1.getName().equals("hello world"));
    }
}
