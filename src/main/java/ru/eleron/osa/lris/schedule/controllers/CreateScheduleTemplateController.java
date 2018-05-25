package ru.eleron.osa.lris.schedule.controllers;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableData;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableDataMarkers;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.message.MessageUtils;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.concurrent.CompletableFuture;

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
    @Autowired
    private ObservableData observableData;
    @Autowired
    private CompositeTaskDao compositeTaskDao;
    @Autowired
    private DayCache dayCache;
    @Autowired
    private MessageUtils messageUtils;

    private ObservableList<CompositeTask> dayTemplateTaskObservableList;
    private ObservableList<CompositeTask> taskInTemplateObservableList;

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
        dayTemplateTaskObservableList = observableData.getData(ObservableDataMarkers.DAY_TASK_TEMPLATES.getValue());
        taskInTemplateObservableList = FXCollections.observableArrayList();
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        compositeTaskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_COMPOSITE_TASK.getMessage()));

        compositeTaskListView.setCellFactory(cell -> new ListCell<CompositeTask>()
        {
            @Override
            protected void updateItem(CompositeTask task, boolean empty)
            {
                super.updateItem(task, empty);
                if (empty || task == null)
                {
                    setText(null);
                } else {
                    setText(task.getName() + " " + task.getTime() + "м " + task.getScore());
                }
            }
        });
        compositeTaskListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> compositeTaskTableView.getItems().setAll(observable.getValue().getChildren())
        );

        nameCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        timeCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getTime()));
        scoreCompositeTaskTableColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getScore()));

        BooleanBinding isSelectItem = compositeTaskListView.getSelectionModel().selectedItemProperty().isNull();
        deleteScheduleTemplateButton.disableProperty().bind(isSelectItem);

        compositeTaskListView.setItems(dayTemplateTaskObservableList);

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
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.CREATE_SCHEDULE_MENU.getUrl()));
    }
    public void deleteScheduleTemplateButtonClicked(ActionEvent event)
    {
        final CompositeTask selectedCompositeTask = compositeTaskListView.getSelectionModel().getSelectedItem();
        CompletableFuture
                .supplyAsync(() -> {
                    if (dayCache.getTemplateScheduleForDay() == null) return false;
                    return dayCache.getTemplateScheduleForDay().equals(selectedCompositeTask)?true:false;
                })
                .exceptionally(e ->
                {
                    logger.info("Error then delete task " + selectedCompositeTask, e);
                    return null;
                })
                .thenAcceptAsync(result -> Platform.runLater(() ->
                {
                    if (result)
                    {
                        messageUtils.showInfoMessage("Шаблон выбран для занятий! Нельзя удалить!");
                    } else {
                        compositeTaskDao.delete(selectedCompositeTask);
                        dayTemplateTaskObservableList.remove(selectedCompositeTask);
                    }
                }));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
    public void createOrDeleteTaskTemplateButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.TASK_MANAGER_MENU.getUrl()));
    }
}
