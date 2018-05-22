package ru.eleron.osa.lris.schedule.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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


/**
 * Controller for frame EntTask
 * @author reyzor
 * @version 1.0
 * @since 22.05.2018
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EndTaskController implements FrameControllerBaseDelegeteIF
{
    private final static Logger logger = LogManager.getLogger(EndTaskController.class);

    @FXML
    private Label nameOfTaskLabel;
    @FXML
    private ChoiceBox<MarkForTask> markForTaskChoiceBox;
    @FXML
    private Button markButton;

    @Autowired
    private StatisticClassDao statisticClassDao;
    @Autowired
    private DayCache dayCache;

    private CompositeTask compositeTask;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void delegete(Object object)
    {
        if (object instanceof CompositeTask)
        {
            this.compositeTask = (CompositeTask) object;
            nameOfTaskLabel.setText(compositeTask.getName());
        }
    }

    @Override
    public void initData()
    {
        markForTaskChoiceBox.setConverter(new StringConverter<MarkForTask>() {
            @Override
            public String toString(MarkForTask object) {
                return "Оценка " + object.getMark();
            }

            @Override
            public MarkForTask fromString(String string) {
                return MarkForTask.getMark(Float.valueOf(string.replace("Оценка ", "")));
            }
        });
        markForTaskChoiceBox.setItems(FXCollections.observableArrayList(MarkForTask.values()));
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

    public void setMarkForTask(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        formedMarkForTask(event);
    }
    private void formedMarkForTask(ActionEvent event)
    {
        if (markForTaskChoiceBox.getValue() != null) {
            final StatisticClass statisticClass = new StatisticClass(new CompositeTaskProxyCompositeKey(compositeTask, dayCache.getScheduleForDay()), markForTaskChoiceBox.getValue());
            statisticClassDao.save(statisticClass);
            dayCache.getMarksForTask().add(statisticClass);
            ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
        }
    }
}
