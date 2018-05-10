package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;

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
    }
    public void backButtonClicked(ActionEvent event)
    {
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
}
