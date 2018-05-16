package ru.eleron.osa.lris.schedule.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableData;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableDataMarkers;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.concurrent.CompletableFuture;

/**
 * Controller for fxml TaskCreateUpdateMenu
 * base functional is create of update tasks
 * @author reyzor
 * @version 1.0
 * @since 10.05.2018
 */

@Component
public class TaskCreateUpdateMenuController implements FrameControllerBaseIF {

    private static final Logger logger = LogManager.getLogger(TaskCreateUpdateMenuController.class);

    @FXML
    private TextField nameTaskTextField;
    @FXML
    private Label fillNameWarningLabel;
    @FXML
    private Spinner<Integer> timeSpinner;
    @FXML
    private Spinner<Integer> scoreSpinner;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    @Autowired
    private MainMenuController mainMenuController;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private ObservableData observableData;
    @Autowired
    private CompositeTaskDao compositeTaskDao;

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
        timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 180, 5, 5));
        scoreSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fillNameWarningLabel.setVisible(false);
        if (compositeTask != null)
        {
            nameTaskTextField.setText(compositeTask.getName());
            timeSpinner.getValueFactory().setValue(compositeTask.getTime());
            scoreSpinner.getValueFactory().setValue(compositeTask.getScore());
        }
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {
        nameTaskTextField.setTooltip(new Tooltip("Название задания"));
        timeSpinner.setTooltip(new Tooltip("Время выполнение задания"));
        scoreSpinner.setTooltip(new Tooltip("Очки за задание"));
        addButton.setTooltip(new Tooltip("Сохранить/обновить"));
        backButton.setTooltip(new Tooltip("Назад"));
    }

    public void addButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        String name = nameTaskTextField.getText().trim();
        if (name.isEmpty() || name.equals(""))
        {
            //show warning message if name don't feel
            fillNameWarningLabel.setText(ConstantsForElements.LABEL_FIELD_DONT_FILL.getMessage());
            fillNameWarningLabel.setVisible(true);
        } else {
            //add element
            if (((ObservableList<CompositeTask>) observableData.getData("TABLE_TASK_TEMPLATE")).stream().anyMatch(compositeTask -> compositeTask.getName().equals(name)) && (compositeTask == null || !compositeTask.getName().equals(name)))
            {
                //show warning message if name equals to another name of other composite task
                fillNameWarningLabel.setText(ConstantsForElements.LABEL_FIELD_NAME_EXIST.getMessage());
                fillNameWarningLabel.setVisible(true);
            } else {
                if (compositeTask == null)
                {
                    saveCompositeTask();
                } else {
                    updateCompositeTask();
                }
            }
        }
    }
    public void backButtonClicked(ActionEvent event)
    {
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }

    /**
     * Task builder
     * Task build according information in frame
     * @return CompositeTask
     * */

    private CompositeTask createTask()
    {
        final CompositeTask compositeTaskTemp = new CompositeTask();
        compositeTaskTemp.setName(nameTaskTextField.getText().trim());
        compositeTaskTemp.setTime(timeSpinner.getValue());
        compositeTaskTemp.setScore(scoreSpinner.getValue());
        return compositeTaskTemp;
    }

    /**
     * Task filler
     * Task fill according information in frame     *
     * */

    private void fillTask()
    {
        compositeTask.setName(nameTaskTextField.getText());
        compositeTask.setTime(timeSpinner.getValue());
        compositeTask.setScore(scoreSpinner.getValue());
    }

    /**
     * Set composite task if it have to be edit
     * */

    public void setCompositeTask(CompositeTask compositeTask)
    {
        this.compositeTask = compositeTask;
    }

    /**
     * Method save new Task at DB and return to previous frame
     * */

    private void saveCompositeTask()
    {
        final CompositeTask compositeTaskTemp = createTask();
        CompletableFuture.runAsync(() -> {
            compositeTaskDao.save(compositeTaskTemp);
        })
                .exceptionally(e -> {
                    logger.error("Error then save task " + compositeTaskTemp, e);
                    return null;
                })
                .thenRunAsync(()-> Platform.runLater(() -> {
            ((ObservableList<CompositeTask>) observableData.getData(ObservableDataMarkers.TASK_TEMPLATES.getValue())).add(compositeTaskDao.getByName(compositeTaskTemp.getName()));
            fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
        }) );
    }

    /**
     * Method update current task at DB and return to previous frame
     * */

    private void updateCompositeTask()
    {
        fillTask();
        CompletableFuture.runAsync(() -> {
            compositeTaskDao.save(compositeTask);
        }).exceptionally(e -> {
            logger.error("Error then updating task " + compositeTask, e);
            return null;
        }).thenRunAsync(()-> Platform.runLater(() -> {
            fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
        }) );
    }
}
