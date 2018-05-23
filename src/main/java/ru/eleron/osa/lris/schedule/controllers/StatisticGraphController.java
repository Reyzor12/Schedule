package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Graph for statistic
 * @author Reyzor
 * @version 1.0
 * @since 13.05.2018
 */

@Component
public class StatisticGraphController implements FrameControllerBaseIF
{
    private static final Logger logger = LogManager.getLogger(StatisticGraphController.class);

    @FXML
    private LineChart<Date, Number> statisticBarChar;
    @FXML
    private Axis<Number> yAxis;
    @FXML
    private Axis<Date> xAxis;

    @Autowired
    private DayCache dayCache;

    private Map<Date, Long> weekMap;

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
        weekMap = new HashMap<>();
        List<StatisticClass> weekStatistic = dayCache.getWeekStatistic(LocalDateTime.now()).stream().filter(statisticClass -> !statisticClass.getMark().equals(MarkForTask.MARK_F)).collect(Collectors.toList());
        dayCache.getWeekProxyCompositeTasks(LocalDateTime.now()).stream().forEach(proxyCompositeTask -> {
            weekMap.put(Date.from(proxyCompositeTask.getDate().atZone(ZoneId.systemDefault()).toInstant()), weekStatistic.stream().filter(statisticClass -> statisticClass.getCompositeKey().getProxyCompositeTask().equals(proxyCompositeTask)).count());
        });
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements()
    {
        
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {

    }
}
