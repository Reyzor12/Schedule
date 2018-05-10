package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

/**
 * Controller for frame CreateScheduleTemplate
 * @author reyzor
 * @version 1.0
 * @since 10.05.2018
 * */

@Component
public class CreateScheduleTemplateController implements FrameControllerBaseIF
{

    private static final Logger logger = LogManager.getLogger(CreateScheduleTemplateController.class);

    @FXML
    private ListView<CompositeTask> compositeTaskListView;
    @FXML
    private TableView<CompositeTask> compositeTaskTableView;
    @FXML
    private TableColumn<CompositeTask, String> nameCompositeTaskTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> timeCompositeTaskTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> scoreCompositeTaskTableColumn;
    @FXML
    private Button addScheduleTemplateButton;
    @FXML
    private Button deleteScheduleTemplateButton;
    @FXML
    private Button createOrDeleteTaskTemplateButton;

    @Autowired
    private MainMenuController mainMenuController;
    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;

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
        compositeTaskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_COMPOSITE_TASK.getMessage()));
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {
        compositeTaskListView.setTooltip(new Tooltip("Список шаблонов задач"));
        compositeTaskTableView.setTooltip(new Tooltip("Описание выбранного щаблона задач"));
        addScheduleTemplateButton.setTooltip(new Tooltip("Добавить шаблон задач"));
        deleteScheduleTemplateButton.setTooltip(new Tooltip("Удалить шаблон задач"));
        createOrDeleteTaskTemplateButton.setTooltip(new Tooltip("Добавить/удалить задачу"));
    }

    public void addScheduleTemplateButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        System.out.println("addSchedulteTemplateButtonClicked clicked");
    }
    public void deleteScheduleTemplateButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        System.out.println("deleteScheduleTemplateButton clicked");
    }
    public void createOrDeleteTaskTemplateButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
    }
}
