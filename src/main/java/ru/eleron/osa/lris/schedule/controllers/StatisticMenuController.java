package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;

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
    public void enableTooltips() {
        weekButton.setTooltip(new Tooltip("Статистика за неделю"));
        monthButton.setTooltip(new Tooltip("Статистика за месяц"));
        showGraphicButton.setTooltip(new Tooltip("Показать график"));
        clearStatisticButton.setTooltip(new Tooltip("Очистить статистику"));
    }

    public void showStatisticForWeek(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
    public void showStatisticForMonth(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
    public void clearAllStatistic(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
    public void showGraphic(ActionEvent event)
    {
        fadeNodeControl.changeSceneWithFade(statisticAnchorPane, (Node)springFxmlLoader.load(ScenesInApplication.STATISTIC_GRAPH.getUrl()));
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
    }
}
