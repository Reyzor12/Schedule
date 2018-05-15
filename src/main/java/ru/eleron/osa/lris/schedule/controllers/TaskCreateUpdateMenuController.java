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
import ru.eleron.osa.lris.schedule.utils.cache.TaskTemplateCache;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.Arrays;
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

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData() {
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 180, 5, 5));
        scoreSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fillNameWarningLabel.setVisible(false);
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
            //show warning message
            fillNameWarningLabel.setText(ConstantsForElements.LABEL_FIELD_DONT_FILL.getMessage());
            fillNameWarningLabel.setVisible(true);
        } else {
            System.out.println(1);
            System.out.println(((ObservableList<CompositeTask>) observableData.getData("TABLE_TASK_TEMPLATE")).isEmpty());
            ((ObservableList<CompositeTask>) observableData.getData("TABLE_TASK_TEMPLATE")).stream().forEach(compositeTask -> System.out.println(compositeTask));
            System.out.println(2);
            //add element
            if (!((ObservableList<CompositeTask>) observableData.getData("TABLE_TASK_TEMPLATE")).stream().anyMatch(compositeTask -> compositeTask.getName().equals(name)))
            {
                fillNameWarningLabel.setText(ConstantsForElements.LABEL_FIELD_NAME_EXIST.getMessage());
                fillNameWarningLabel.setVisible(true);
            } else {
                final CompositeTask compositeTask = createTask();
                CompletableFuture.runAsync(() -> {
                    compositeTaskDao.save(compositeTask);
                }).thenRunAsync(()-> Platform.runLater(() -> {
                    ((ObservableList<CompositeTask>) observableData.getData("TABLE_TASK_TEMPLATE")).add(compositeTaskDao.getByName(compositeTask.getName()));
                }) );
            }
        }
    }
    public void backButtonClicked(ActionEvent event)
    {
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }

    private CompositeTask createTask()
    {
        final CompositeTask compositeTask = new CompositeTask();
        compositeTask.setName(nameTaskTextField.getText().trim());
        compositeTask.setTime(timeSpinner.getValue());
        compositeTask.setScore(scoreSpinner.getValue());
        return compositeTask;
    }
}
