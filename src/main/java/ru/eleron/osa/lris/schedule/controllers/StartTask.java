package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.StatisticClassDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTaskProxyCompositeKey;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseDelegeteIF;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

/**
 * Controller for information on start task
 * @author reyzor
 * @version 1.0
 * @since 21.05.2018
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StartTask implements FrameControllerBaseDelegeteIF
{
    private final static Logger logger = LogManager.getLogger(StartTask.class);

    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label titleOfTask;

    @Autowired
    private StatisticClassDao statisticClassDao;
    @Autowired
    private DayCache dayCache;

    private CompositeTask compositeTask;

    public void initialize()
    {
        System.out.println("Hello world");
    }

    @Override
    public void initData() {

    }

    @Override
    public void configureElements()
    {

    }

    @Override
    public void enableTooltips() {

    }

    public void delegete(Object compositeTask)
    {
        this.compositeTask = (CompositeTask) compositeTask;
        titleOfTask.setText(this.compositeTask.getName());
    }

    public void applyButtonClick(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
    public void cancelButtonClick(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        statisticClassDao.save(new StatisticClass(new CompositeTaskProxyCompositeKey(compositeTask, dayCache.getScheduleForDay()), MarkForTask.MARK_F));
        System.out.println(statisticClassDao.performedTask(dayCache.getScheduleForDay()));
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
}
