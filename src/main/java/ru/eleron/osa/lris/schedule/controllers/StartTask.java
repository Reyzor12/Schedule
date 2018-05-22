package ru.eleron.osa.lris.schedule.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.StatisticClassDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTaskProxyCompositeKey;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseDelegeteIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsSittings;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

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
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private BaseSceneLoader sceneLoader;

    private CompositeTask compositeTask;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData()
    {
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
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
        taskScheduler.schedule(
                () -> Platform.runLater(() ->
                        sceneLoader.loadSceneWithoutCache(
                        ScenesInApplication.END_TASK.getUrl(),
                        compositeTask,
                        configureStage(new Stage())
                ))
                ,
                Date.from(dayCache
                        .getTaskWithTime()
                        .get(compositeTask)
                        .getEnd()
                        .atDate(LocalDate.now())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
        );
        /*taskScheduler.schedule(
                () -> Platform.runLater(() ->
                        sceneLoader.loadSceneWithoutCache(
                                ScenesInApplication.END_TASK.getUrl(),
                                compositeTask,
                                configureStage(new Stage())
                        ))
                ,
                Date.from(LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant())
        );*/
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
    public void cancelButtonClick(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final StatisticClass statisticClass = new StatisticClass(new CompositeTaskProxyCompositeKey(compositeTask, dayCache.getScheduleForDay()), MarkForTask.MARK_F);
        statisticClassDao.save(statisticClass);
        dayCache.getMarksForTask().add(statisticClass);
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
    private Stage configureStage(Stage stage)
    {
        stage.setTitle(ConstantsSittings.APPLICATION_TITLE.getStringConstant());
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ConstantsSittings.APPLICATION_ICON_PATH.getStringConstant())));
        return stage;
    }
}
