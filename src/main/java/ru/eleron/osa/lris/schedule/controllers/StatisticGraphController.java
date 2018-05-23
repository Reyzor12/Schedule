package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.MarkForTask;
import ru.eleron.osa.lris.schedule.database.entities.StatisticClass;
import ru.eleron.osa.lris.schedule.utils.cache.DayCache;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseDelegeteIF;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Graph for statistic
 * @author Reyzor
 * @version 1.0
 * @since 13.05.2018
 */

@Component
public class StatisticGraphController implements FrameControllerBaseDelegeteIF
{
    private static final Logger logger = LogManager.getLogger(StatisticGraphController.class);

    @FXML
    private LineChart<String, Number> statisticBarChar;
    @FXML
    private Axis<Number> yAxis;
    @FXML
    private Axis<String> xAxis;

    @Autowired
    private DayCache dayCache;

    private XYChart.Series<String, Number> chartData;

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
        
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips()
    {

    }

    @Override
    public void delegete(Object object) {
        chartData = (XYChart.Series<String, Number>) object;
        statisticBarChar.getData().setAll(chartData);
    }
}
