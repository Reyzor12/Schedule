package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;


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
    private LineChart statisticBarChar;

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
}
