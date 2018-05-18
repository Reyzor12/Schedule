package ru.eleron.osa.lris.schedule.controllers;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
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
import ru.eleron.osa.lris.schedule.database.dao.ProxyCompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableData;
import ru.eleron.osa.lris.schedule.utils.cache.ObservableDataMarkers;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.message.MessageUtils;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;
import ru.eleron.osa.lris.schedule.utils.uielements.SpinnerForSchedule;
import ru.eleron.osa.lris.schedule.utils.uielements.SpinnerForScheduleIF;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class ChoosePlanForDayController implements FrameControllerBaseIF{

    private final static Logger logger = LogManager.getLogger(ChoosePlanForDayController.class);

    @FXML
    private ImageView hourPositionSecondImageView;
    @FXML
    private ImageView minutePositionFirstImageView;
    @FXML
    private ImageView minutePositionSecondImageView;
    @FXML
    private ImageView deviderImageView;
    @FXML
    private Button increaseButton;
    @FXML
    private Button decreaseButton;
    @FXML
    private Button chooseTaskButton;
    @FXML
    private ListView<CompositeTask> templatesListView;

    @Autowired
    private ObservableData observableData;
    @Autowired
    private DayCache dayCache;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private MainMenuController mainMenuController;
    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private ProxyCompositeTaskDao proxyCompositeTaskDao;

    private ObservableList<CompositeTask> dayTemplateCompositeTaskList;
    private SpinnerForScheduleIF<Image> spinner;

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
        dayTemplateCompositeTaskList = observableData.getData(ObservableDataMarkers.DAY_TASK_TEMPLATES.getValue());
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements() {
        templatesListView.setPlaceholder(new Label(ConstantsForElements.EMPTY_CURRENT_TASK_TABLE.getMessage()));

        spinner = new SpinnerForSchedule(Arrays.asList(8.00d,8.30d, 9.00d));
        deviderImageView.setImage(new Image(getClass().getClassLoader().getResource(ConstantsForElements.DEVISION.getMessage()).toString()));
        setSpinnerTime(spinner.getCurrentValue());
        decreaseButton.setDisable(true);

        BooleanBinding isDayTemplateChosen = templatesListView.getSelectionModel().selectedItemProperty().isNull();
        chooseTaskButton.disableProperty().bind(isDayTemplateChosen);

        templatesListView.setCellFactory(cell -> new ListCell<CompositeTask>()
        {
            @Override
            public void updateItem(CompositeTask task, boolean empty)
            {
                super.updateItem(task, empty);
                if (empty || task == null)
                {
                    setText(null);
                } else {
                    setText(task.getName() + " " + task.getTime() + "с " + task.getScore() + " очков");
                }
            }
        });

        templatesListView.setItems(dayTemplateCompositeTaskList);

        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {
        increaseButton.setTooltip(new Tooltip("Увеличить время"));
        decreaseButton.setTooltip(new Tooltip("Уменьшить время"));
        chooseTaskButton.setTooltip(new Tooltip("Выбрать шаблон"));
        templatesListView.setTooltip(new Tooltip("Шаблоны распорядка дня"));
    }

    /**
     * change image on spinner
     * */

    private void setSpinnerTime(List<Image> spinnerTime)
    {
        hourPositionSecondImageView.setImage(spinnerTime.get(0));
        minutePositionFirstImageView.setImage(spinnerTime.get(1));
        minutePositionSecondImageView.setImage(SpinnerForSchedule.NUMBER_HASH.get(0));
    }

    public void increaseButtonClicked(ActionEvent event)
    {
        if (decreaseButton.isDisable()) decreaseButton.setDisable(false);
        setSpinnerTime(spinner.next());
        if (!spinner.hasNext()) ((Button)event.getSource()).setDisable(true);
    }
    public void decreaseButtonClicked(ActionEvent event)
    {
        if (increaseButton.isDisable()) increaseButton.setDisable(false);
        setSpinnerTime(spinner.previous());
        if (!spinner.hasPrevious()) ((Button)event.getSource()).setDisable(true);
    }
    public void chooseTaskButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        if (dayCache.isDefine())
        {
            messageUtils.showInfoMessage("На сегодян уже выбран список занятий!");
        } else {
            saveAndChooseDaySchedule();
        }
    }

    /**
     * create new {@link ProxyCompositeTask} object with parameters from frame
     * save it in database, update {@link DayCache}, and return to main menu
     * If exception then show message
     * */

    private void saveAndChooseDaySchedule()
    {
        final CompositeTask selectedDayTemplate = templatesListView.getSelectionModel().getSelectedItem();
        final ProxyCompositeTask createdProxyCompositeTask = new ProxyCompositeTask();

        createdProxyCompositeTask.setName(selectedDayTemplate.getName());
        createdProxyCompositeTask.setDate(LocalDateTime.now());
        createdProxyCompositeTask.setCompositeTask(selectedDayTemplate);

        CompletableFuture
                .supplyAsync(() ->
                {
                    proxyCompositeTaskDao.save(createdProxyCompositeTask);
                    return true;
                })
                .exceptionally(e ->
                {
                    logger.error("Ошибка сохранения объекта " + createdProxyCompositeTask, e);
                    return false;
                })
                .thenAcceptAsync(result -> Platform.runLater(() ->
                {
                    if (result)
                    {
                        dayCache.setTemplateScheduleForDay(selectedDayTemplate);
                        dayCache.setScheduleForDay(createdProxyCompositeTask);
                        fadeNodeControl.changeSceneWithFade(mainMenuController.getInformationAnchorPane(), (Node) springFxmlLoader.load(ScenesInApplication.SCHEDULE_TABLE_NOW.getUrl()));
                    } else {
                        messageUtils.showInfoMessage("Не удалось сохранить проект");
                    }
                }));
    }
}
