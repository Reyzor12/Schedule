package ru.eleron.osa.lris.schedule.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.ProxyCompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.cache.WrapInTimeCompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Controller for inner frame with table data of schedule
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

@Component
public class ScheduleTableNowController implements FrameControllerBaseIF
{
    private final static Logger logger = LogManager.getLogger(ScheduleTableNowController.class);

    @FXML
    private TableView<CompositeTask> taskTableView;
    @FXML
    private TableColumn<CompositeTask, String> taskNameColumn;
    @FXML
    private TableColumn<CompositeTask, String> taskStartColumn;
    @FXML
    private TableColumn<CompositeTask, String> taskEndColumn;
    @FXML
    private TableColumn<CompositeTask, String> taskMarkColumn;

    @Autowired
    private DayCache dayCache;
    @Autowired
    private ProxyCompositeTaskDao proxyCompositeTaskDao;

    private ObservableList<CompositeTask> compositeTaskInScheduleForToday;
    private Map<CompositeTask, WrapInTimeCompositeTask> wrapInTimeCompositeTaskMap;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData() {
        compositeTaskInScheduleForToday = FXCollections.observableArrayList();
        wrapInTimeCompositeTaskMap = new HashMap<>();
        checkForDayCache();
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        taskNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        taskStartColumn.setCellValueFactory(cell -> new SimpleObjectProperty(wrapInTimeCompositeTaskMap.get(cell.getValue()).getStart()));
        taskEndColumn.setCellValueFactory(cell -> new SimpleObjectProperty(wrapInTimeCompositeTaskMap.get(cell.getValue()).getEnd()));
        taskMarkColumn.setCellValueFactory(cell -> new SimpleObjectProperty(wrapInTimeCompositeTaskMap.get(cell.getValue()).getMark()));
        taskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_CURRENT_TASK_TABLE.getMessage()));
        taskTableView.setItems(compositeTaskInScheduleForToday);
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {
        taskTableView.setTooltip(new Tooltip("Задачи на сегодня"));
    }

    /**
     * Check actual cache for user
     * */

    private void checkForDayCache()
    {
        final LocalDate localDate = LocalDate.now();
        final ProxyCompositeTask proxyCompositeTaskForCurrentDay = proxyCompositeTaskDao.findByDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        if (proxyCompositeTaskForCurrentDay != null)
        {
            dayCache.setTemplateScheduleForDay(proxyCompositeTaskForCurrentDay.getCompositeTask());
            dayCache.setScheduleForDay(proxyCompositeTaskForCurrentDay);
            compositeTaskInScheduleForToday.setAll(dayCache.getTemplateScheduleForDay().getChildren());
            wrapInTimeCompositeTaskMap.putAll(dayCache.getTaskWithTime());
            if (!wrapInTimeCompositeTaskMap.get(dayCache.getTemplateScheduleForDay().getChildren().get(dayCache.getTemplateScheduleForDay().getChildren().size()-1)).getStart().isBefore(LocalTime.now()))
            {
                TaskScheduler t = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

                t.schedule(() -> {
                    System.out.println("HELLO WORLD");
                },new Date());

            }

        }
    }
}
