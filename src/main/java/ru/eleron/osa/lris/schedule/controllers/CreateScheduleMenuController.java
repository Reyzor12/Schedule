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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.CompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.TypeOfCompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableData;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableDataMarkers;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.concurrent.CompletableFuture;

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
    @FXML
    private Label infoLabel;

    @Autowired
    private ObservableData observableData;
    @Autowired
    private CompositeTaskDao compositeTaskDao;
    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private MainMenuController mainMenuController;

    private ObservableList<CompositeTask> templateCompositeTaskObservableList;
    private ObservableList<CompositeTask> dayTemplateCompositeTaskObservableList;
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

        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        infoLabel.setVisible(false);

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
                    setText(item.getName() + " " + item.getTime() + "м " + item.getScore());
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

    /**
     * Change image on buttons if user hover mouse on it
     * */

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

    /**
     * add Task Template into the Day Template
     * and update statistic information
     * */

    public void addTaskToDayTemplate(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final CompositeTask compositeTaskTemp = compositeTaskTableView.getSelectionModel().getSelectedItem().clone();
        compositeTaskTemp.setType(TypeOfCompositeTask.TASK_IN_DAY);
        compositeTaskTemp.setId(null);
        compositeTaskListView.getItems().add(compositeTaskTemp);
        timeCompositeTask += compositeTaskTemp.getTime();
        scoreCompositeTask += compositeTaskTemp.getScore();
        refreshLabelStatistic();
    }

    /**
     * remove Task Template from the Day Template
     * and update statistic information
     * */

    public void removeTaskFromDayTemplate(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final CompositeTask compositeTaskTemp = compositeTaskListView.getSelectionModel().getSelectedItem();
        compositeTaskListView.getItems().remove(compositeTaskTemp);
        timeCompositeTask -= compositeTaskTemp.getTime();
        scoreCompositeTask -= compositeTaskTemp.getScore();
        refreshLabelStatistic();
    }

    /**
     * Collect data from frame and produce Day Template {@link CompositeTask}
     * after that, method save it in database
     * */

    public void saveDayTemplate(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        final String name = nameTextField.getText();
        if (name.isEmpty())
        {
            setInformation(ConstantsForElements.LABEL_FIELD_NAME_DONT_GET.getMessage());
            return;
        } else if (checkUniqueName(name))
        {
            setInformation(ConstantsForElements.LABEL_FIELD_NAME_EXIST.getMessage());
            return;
        }
        final ObservableList<CompositeTask> children = compositeTaskListView.getItems();
        if (children.isEmpty())
        {
            setInformation(ConstantsForElements.EMPTY_COMPOSITE_TASK.getMessage());
            return;
        }
        final CompositeTask dayTemplateForSave = new CompositeTask(name, TypeOfCompositeTask.DAY, children);
        saveDayTemplateInDatabase(dayTemplateForSave);
    }

    /**
     * Refresh text in label timeLabel and scoreLabel with new values of
     * timeCompositeTask and scoreCompositeTask
     * */

    private void refreshLabelStatistic()
    {
        Integer hours = timeCompositeTask / 60;
        Integer minutes = timeCompositeTask % 60;
        timeLabel.setText(hours + ":" + minutes);
        scoreLabel.setText(scoreCompositeTask.toString());
    }

    /**
     * Check name of Task Day for it unique
     * @param name - checked name
     * */

    private boolean checkUniqueName(String name)
    {
        dayTemplateCompositeTaskObservableList = observableData.getData(ObservableDataMarkers.DAY_TASK_TEMPLATES.getValue());
        if (dayTemplateCompositeTaskObservableList == null || dayTemplateCompositeTaskObservableList.isEmpty()) return false;
        if (dayTemplateCompositeTaskObservableList.stream().anyMatch(item -> item.getName().equals(name))) return true;
        return false;
    }

    /**
     * show information in infoLabel
     * @param information - text of information
     * */

    private void setInformation(String information)
    {
        infoLabel.setVisible(true);
        infoLabel.setText(information);
    }

    /**
     * save compositeTask in database and back to previous scene
     * */

    private void saveDayTemplateInDatabase(CompositeTask compositeTaskTemp)
    {
        CompletableFuture
                .runAsync(() -> dayTemplateCompositeTaskObservableList.add(compositeTaskDao.save(compositeTaskTemp)))
                .exceptionally(e -> {
                    logger.info("Error then save task " + compositeTaskTemp, e);
                    return null;
                })
                .thenRunAsync(() -> Platform.runLater(() -> {
                    //dayTemplateCompositeTaskObservableList.add(compositeTaskDao.getByName(compositeTaskTemp.getName()));
                    fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node)springFxmlLoader.load(ScenesInApplication.CREATE_SCHEDULE_TEMPLATE.getUrl()));
                }));
    }
}
