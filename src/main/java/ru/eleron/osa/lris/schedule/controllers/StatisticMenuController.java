package ru.eleron.osa.lris.schedule.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.dao.ProxyCompositeTaskDao;
import ru.eleron.osa.lris.schedule.database.dao.StatisticClassDao;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.message.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for view statistics of tasks
 * @author Reyzor
 * @version 1.0
 * @since 12.05.2018
 */

@Component
public class StatisticMenuController implements FrameControllerBaseIF
{
    private static final Logger logger = LogManager.getLogger(StatisticMenuController.class);
    private static final String DEFAULT_VALUE = "0";

    @FXML
    private Button weekButton;
    @FXML
    private Button monthButton;
    @FXML
    private Button clearStatisticButton;
    @FXML
    private Label countTaskDoneLabel;
    @FXML
    private Label countTaskFailLabel;
    @FXML
    private Label countScoreReceiveLabel;
    @FXML
    private Label countScoreLostLabel;
    @FXML
    private Button showGraphicButton;
    @FXML
    private AnchorPane statisticAnchorPane;

    @Autowired
    private FadeNodeControl fadeNodeControl;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private DayCache dayCache;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private StatisticClassDao statisticClassDao;
    @Autowired
    private ProxyCompositeTaskDao proxyCompositeTaskDao;

    private List<StatisticClass> weekStatistic;
    private List<StatisticClass> monthStatistic;
    private String passTaskWeek;
    private String missTaskWeek;
    private String scoreReceivedWeek;
    private String scoreLostWeek;
    private String passTaskMonth;
    private String missTaskMonth;
    private String scoreReceivedMonth;
    private String scoreLostMonth;
    private Map<LocalDate, Long> weekMap;
    private Map<LocalDate, Long> monthMap;
    private XYChart.Series<String,Number> chartData;
    private ObservableList<XYChart.Data<String,Number>> observableList;
    private List<XYChart.Data<String,Number>> weekPoints;
    private List<XYChart.Data<String,Number>> monthPoints;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        weekButton.fire();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData()
    {
        passTaskWeek = DEFAULT_VALUE;
        missTaskWeek = DEFAULT_VALUE;
        scoreReceivedWeek = DEFAULT_VALUE;
        scoreLostWeek = DEFAULT_VALUE;
        passTaskMonth = DEFAULT_VALUE;
        missTaskMonth = DEFAULT_VALUE;
        scoreReceivedMonth = DEFAULT_VALUE;
        scoreLostMonth = DEFAULT_VALUE;
        weekMap = new HashMap<>();
        monthMap = new HashMap<>();
        chartData = new XYChart.Series<>();
        observableList = FXCollections.observableArrayList();
        chartData.setData(observableList);
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {
        weekButton.setTooltip(new Tooltip("Статистика за неделю"));
        monthButton.setTooltip(new Tooltip("Статистика за месяц"));
        showGraphicButton.setTooltip(new Tooltip("Показать график"));
        clearStatisticButton.setTooltip(new Tooltip("Очистить статистику"));
    }

    public void showStatisticForWeek(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        weekButton.setStyle("-fx-background-color: #00E020;-fx-text-fill: black;");
        monthButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #00E020;");
        displayWeekStatistic();
    }
    public void showStatisticForMonth(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        monthButton.setStyle("-fx-background-color: #00E020;-fx-text-fill: black;");
        weekButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #00E020;");
        displayMonthStatistic();
    }
    public void clearAllStatistic(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        Optional<ButtonType> result = messageUtils.showOptionMessage("Предупреждение", "Хотите ли вы обнулить всю статистику?", "Да");
        if (result.get().equals(messageUtils.getButtonTypeAgree()))
        {
            clearStatistic();
        }
    }
    public void showGraphic(ActionEvent event)
    {
        fadeNodeControl.changeSceneWithFade(statisticAnchorPane, (Node)springFxmlLoader.load(ScenesInApplication.STATISTIC_GRAPH.getUrl(), chartData));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }

    private boolean isLoadedWeekStatistic()
    {
        return weekStatistic != null;
    }
    private boolean isLoadedMonthStatistic()
    {
        return monthStatistic != null;
    }
    private void displayWeekStatistic()
    {

        if (!isStatisticForWeekDisplayed())
        {
            if (!isLoadedWeekStatistic()) {
                weekStatistic = dayCache
                        .getWeekStatistic(LocalDateTime.now())
                        .stream()
                        .filter(statisticClass -> !statisticClass.getMark().equals(MarkForTask.MARK_F))
                        .collect(Collectors.toList());
                dayCache.getWeekProxyCompositeTasks(LocalDateTime.now()).stream().forEach(proxyCompositeTask -> {
                    weekMap.put(proxyCompositeTask.getDate().toLocalDate(), weekStatistic.stream().filter(statisticClass -> statisticClass.getCompositeKey().getProxyCompositeTask().equals(proxyCompositeTask)).count());
                });
                formedWeekPointList();
            }

            final Integer present = weekStatistic.size();
            passTaskWeek = String.valueOf(present);
            final Long allTask = dayCache
                                        .getWeekProxyCompositeTasks(LocalDateTime.now())
                                        .stream()
                                        .flatMap(proxyCompositeTask -> proxyCompositeTask.getCompositeTask().getChildren().stream())
                                        .count();
            missTaskWeek = String.valueOf(allTask - present);
            final Integer receive = weekStatistic
                                        .stream()
                                        .mapToInt(statisticClass -> Math.round(statisticClass.getMark().getMark()*statisticClass.getCompositeKey().getCompositeTask().getScore()))
                                        .sum();
            scoreReceivedWeek = String.valueOf(receive);
            final Integer all = dayCache.getWeekProxyCompositeTasks(LocalDateTime.now())
                                    .stream()
                                    .mapToInt(proxyCompositeTask -> proxyCompositeTask.getCompositeTask().getScore())
                                    .sum();
            scoreLostWeek = String.valueOf(all - receive);
        }
        setAllStatisticInLabel(passTaskWeek, missTaskWeek, scoreReceivedWeek, scoreLostWeek);
        observableList.setAll(weekPoints);
    }
    private void displayMonthStatistic()
    {
        if (!isStatisticForMonthDisplayed()) {
            if (!isLoadedMonthStatistic())
            {
                monthStatistic = dayCache
                        .getMonthStatistic(LocalDateTime.now())
                        .stream()
                        .filter(statisticClass -> !statisticClass.getMark().equals(MarkForTask.MARK_F))
                        .collect(Collectors.toList());
                dayCache.getMonthProxyCompositeTasks(LocalDateTime.now()).stream().forEach(proxyCompositeTask -> {
                    monthMap.put(proxyCompositeTask.getDate().toLocalDate(), monthStatistic.stream().filter(statisticClass -> statisticClass.getCompositeKey().getProxyCompositeTask().equals(proxyCompositeTask)).count());
                });
                formedMonthPointList();
            }
            final Integer present = monthStatistic.size();
            passTaskMonth = String.valueOf(present);
            final Long allTask = dayCache
                                    .getMonthProxyCompositeTasks(LocalDateTime.now())
                                    .stream()
                                    .flatMap(proxyCompositeTask -> proxyCompositeTask.getCompositeTask().getChildren().stream())
                                    .count();
            missTaskMonth = String.valueOf(allTask - present);
            final Integer receive = monthStatistic
                                        .stream()
                                        .mapToInt(statisticClass -> Math.round(statisticClass.getMark().getMark()*statisticClass.getCompositeKey().getCompositeTask().getScore()))
                                        .sum();
            scoreReceivedMonth = String.valueOf(receive);
            final Integer all = dayCache
                    .getWeekProxyCompositeTasks(LocalDateTime.now())
                    .stream()
                    .mapToInt(proxyCompositeTask -> proxyCompositeTask.getCompositeTask().getScore())
                    .sum();
            scoreLostMonth = String.valueOf(all - receive);
        }
        setAllStatisticInLabel(passTaskMonth, missTaskMonth, scoreReceivedMonth, scoreLostMonth);
        observableList.setAll(monthPoints);
    }
    private boolean isStatisticForWeekDisplayed()
    {
        return !passTaskWeek.equals(DEFAULT_VALUE);
    }
    private boolean isStatisticForMonthDisplayed()
    {
        return !passTaskMonth.equals(DEFAULT_VALUE);
    }
    private void setAllStatisticInLabel(String passTask, String missTask, String scoreReceived, String scoreLost)
    {
        countTaskDoneLabel.setText(passTask);
        countTaskFailLabel.setText(missTask);
        countScoreReceiveLabel.setText(scoreReceived);
        countScoreLostLabel.setText(scoreLost);
    }
    private void formedWeekPointList()
    {
        weekPoints = weekMap
                .entrySet()
                .stream()
                .map(entry -> new XYChart.Data<String,Number>(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
    private void formedMonthPointList()
    {
        monthPoints = monthMap
                .entrySet()
                .stream()
                .map(entry -> new XYChart.Data<String,Number>(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
    private void clearStatistic()
    {
        statisticClassDao.deleteAll();
        proxyCompositeTaskDao.deleteAll();
        dayCache.dayCacheClear();
        initData();
        setAllStatisticInLabel(DEFAULT_VALUE,DEFAULT_VALUE,DEFAULT_VALUE,DEFAULT_VALUE);
    }
}
