package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

/**
 * Controller for creating templates of schedule
 *
 * @author Reyzor
 * @version 1.0
 * @since 10.05.2018
 */

@Component
public class CreateScheduleMenuController implements FrameControllerBaseIF
{
    private static final Logger logger = LogManager.getLogger(CreateScheduleMenuController.class);

    @FXML
    private Button leftRemoveButton;
    @FXML
    private Button rightAddButton;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView rightImage;
    @FXML
    private Button saveButton;
    @FXML
    private ListView<CompositeTask> compositeTaskListView;
    @FXML
    private TableView<CompositeTask> compositeTaskTableView;
    @FXML
    private TableColumn<CompositeTask, String> nameTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> timeTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> scoreTableColumn;

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
    public void configureElements() {
        compositeTaskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_TASK.getMessage()));
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {
        leftRemoveButton.setTooltip(new Tooltip("Удалить задачу из шаблона"));
        rightAddButton.setTooltip(new Tooltip("Добавить задачу в шаблон"));
        saveButton.setTooltip(new Tooltip("Сохранить шаблон"));
        compositeTaskListView.setTooltip(new Tooltip("Список задач в шаблоне"));
        compositeTaskTableView.setTooltip(new Tooltip("Список всех задач"));
    }

    public void focusLeftButton()
    {
        leftImage.setImage(new Image(getClass().getClassLoader().getResource(ConstantsForElements.ARROW_LEFT_BLACK.getMessage()).toString()));
    }
    public void focusLostLeftButton()
    {
        leftImage.setImage(new Image(getClass().getClassLoader().getResource(ConstantsForElements.ARROW_LEFT.getMessage()).toString()));
    }
    public void focusRightButton()
    {
        rightImage.setImage(new Image(getClass().getClassLoader().getResource(ConstantsForElements.ARROW_RIGHT_BLACK.getMessage()).toString()));
    }
    public void focusLostRightButton()
    {
        rightImage.setImage(new Image(getClass().getClassLoader().getResource(ConstantsForElements.ARROW_RIGHT.getMessage()).toString()));
    }
}
