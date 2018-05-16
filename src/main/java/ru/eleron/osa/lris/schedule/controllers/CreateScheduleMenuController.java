package ru.eleron.osa.lris.schedule.controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableData;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableDataMarkers;
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
    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer DEFAULT_TIME = 0;

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
    private TextField nameTextField;
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
    @FXML
    private Label scoreLabel;
    @FXML
    private Label timeLabel;

    @Autowired
    private ObservableData observableData;

    private ObservableList<CompositeTask> templateCompositeTaskObservableList;
    private Integer timeCompositeTask;
    private Integer scoreCompositeTask;

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
        templateCompositeTaskObservableList = observableData.getData(ObservableDataMarkers.TASK_TEMPLATES.getValue());
        timeCompositeTask = DEFAULT_TIME;
        scoreCompositeTask = DEFAULT_SCORE;

        nameTableColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getName()));
        timeTableColumn.setCellValueFactory(value -> new SimpleObjectProperty(value.getValue().getTime()));
        scoreTableColumn.setCellValueFactory(value -> new SimpleObjectProperty(value.getValue().getScore()));

        compositeTaskListView.setCellFactory(cell -> new ListCell<CompositeTask>() {
            @Override
            protected void updateItem(CompositeTask item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " " + item.getTime() + "c " + item.getScore());
                }
            }
        });

        BooleanBinding isTemplateSelected = compositeTaskTableView.getSelectionModel().selectedItemProperty().isNull();
        BooleanBinding isTaskSelected = compositeTaskListView.getSelectionModel().selectedItemProperty().isNull();

        leftRemoveButton.disableProperty().bind(isTemplateSelected);
        rightAddButton.disableProperty().bind(isTaskSelected);

        compositeTaskTableView.setItems(templateCompositeTaskObservableList);

        timeLabel.setText(timeCompositeTask.toString());
        scoreLabel.setText(scoreCompositeTask.toString());

        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
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
    public void addTaskToDayTemplate()
    {
        final CompositeTask compositeTaskTemp = compositeTaskTableView.getSelectionModel().getSelectedItem();
        compositeTaskListView.getItems().add(compositeTaskTemp);
        timeCompositeTask += compositeTaskTemp.getTime();
        scoreCompositeTask += compositeTaskTemp.getScore();
        timeLabel.setText(timeCompositeTask.toString());
        scoreLabel.setText(scoreCompositeTask.toString());
    }
    public void removeTaskFromDayTemplate()
    {

    }
    public void saveDayTemplate()
    {

    }
}
