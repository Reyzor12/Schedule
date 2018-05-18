package ru.eleron.osa.lris.schedule.controllers;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import ru.eleron.osa.lris.schedule.utils.message.MessageUtils;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * @author reyzor
 * @version 1.0
 * @since 10.05.2018
 */

@Component
public class TaskManagerMenuController implements FrameControllerBaseIF
{

    private static final Logger logger = LogManager.getLogger(TaskManagerMenuController.class);

    @FXML
    private TableView<CompositeTask> compositeTaskTableView;
    @FXML
    private TableColumn<CompositeTask, String> nameCompositeTaskTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> timeCompositeTaskTableColumn;
    @FXML
    private TableColumn<CompositeTask, String> scoreCompositeTaskTableColumn;
    @FXML
    private Button editCompositeTaskButton;
    @FXML
    private Button addCompositeTaskButton;
    @FXML
    private Button removeCompositeTaskButton;

    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private MainMenuController mainMenuController;
    @Autowired
    private ObservableData observableData;
    @Autowired
    private TaskCreateUpdateMenuController taskCreateUpdateMenuController;
    @Autowired
    private CompositeTaskDao compositeTaskDao;
    @Autowired
    private MessageUtils messageUtils;

    private ObservableList<CompositeTask> templatesOfTasks;

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
        templatesOfTasks = observableData.getData(ObservableDataMarkers.TASK_TEMPLATES.getValue());
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        compositeTaskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_TASK_TEMPLATES.getMessage()));
        nameCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        timeCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getTime()));
        scoreCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getScore()));
        BooleanBinding isSelectedItem = compositeTaskTableView.getSelectionModel().selectedItemProperty().isNull();
        editCompositeTaskButton.disableProperty().bind(isSelectedItem);
        removeCompositeTaskButton.disableProperty().bind(isSelectedItem);
        compositeTaskTableView.setItems(templatesOfTasks);
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {
        compositeTaskTableView.setTooltip(new Tooltip("Таблица шаблонов задач"));
        editCompositeTaskButton.setTooltip(new Tooltip("Редактировать задачу"));
        addCompositeTaskButton.setTooltip(new Tooltip("Добавить задачу"));
        removeCompositeTaskButton.setTooltip(new Tooltip("Удалить задачу"));
    }

    public void editCompositeTaskButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final CompositeTask compositeTaskTemp = compositeTaskTableView.getSelectionModel().getSelectedItem();
        taskCreateUpdateMenuController.setCompositeTask(compositeTaskTemp);
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_CREATE_UPDATE_MENU.getUrl()));
    }
    public void addCompositeTaskButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        taskCreateUpdateMenuController.setCompositeTask(null);
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_CREATE_UPDATE_MENU.getUrl()));
    }
    public void removeCompositeTaskButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final CompositeTask compositeTaskTemp = compositeTaskTableView.getSelectionModel().getSelectedItem();
        CompletableFuture
                .supplyAsync(() ->
                {
                    List<CompositeTask> taskUsedInDayTemplate = observableData.getData(ObservableDataMarkers.DAY_TASK_TEMPLATES.getValue());
                    if (taskUsedInDayTemplate.stream().flatMap(task -> task.getChildren().stream()).distinct().anyMatch(task -> task.equals(compositeTaskTemp)))
                    {
                        return true;
                    } else {
                        return false;
                    }
                })
                .exceptionally(e -> {
                    logger.error("Can't delete element " + compositeTaskTemp, e);
                    return true;
                })
                .thenAcceptAsync(result -> Platform.runLater(() ->
                    {
                        if (result)
                        {
                            messageUtils.showInfoMessage(ConstantsForElements.CANT_REMOVE_TASK_IN_DAY.getMessage());
                        } else {
                            compositeTaskDao.delete(compositeTaskTemp);
                            observableData.getData(ObservableDataMarkers.TASK_TEMPLATES.getValue()).remove(compositeTaskTemp);
                        }
                    })
                );
    }
}
