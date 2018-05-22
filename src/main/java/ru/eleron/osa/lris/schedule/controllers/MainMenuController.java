package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FadeNodeControl;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;

/**
 * Class controller for main menu of application
 * @author reyzor
 * @version 1.0
 * @since 04.05.2018
 * */

@Component
public class MainMenuController implements FrameControllerBaseIF
{
    private static final Logger logger = LogManager.getLogger(MainMenuController.class);

    @FXML
    private AnchorPane informationAnchorPane;
    @FXML
    private Button chooseButton;
    @FXML
    private Button createButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private Button planButton;

    @Autowired
    private BaseSceneLoader sceneLoader;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private FadeNodeControl fadeNodeControl;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    public void configureElements()
    {
        fadeNodeControl.loadSceneInPane(informationAnchorPane, (Parent) springFxmlLoader.load(ScenesInApplication.SCHEDULE_TABLE_NOW.getUrl()));
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    public void initData()
    {
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    public void enableTooltips()
    {
        chooseButton.setTooltip(new Tooltip("Выбрать план на день"));
        createButton.setTooltip(new Tooltip("Составить план на день"));
        statisticsButton.setTooltip(new Tooltip("Посмотреть статистику"));
        planButton.setTooltip(new Tooltip("Показать план на день"));
    }

    public void chooseButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(informationAnchorPane, (Node) springFxmlLoader.load(ScenesInApplication.CHOOSE_PLAN_FOR_DAY.getUrl()));
    }
    public void createButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(informationAnchorPane, (Node) springFxmlLoader.load(ScenesInApplication.CREATE_SCHEDULE_TEMPLATE.getUrl()));
    }
    public void statisticsButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(informationAnchorPane, (Node) springFxmlLoader.load(ScenesInApplication.STATISTIC_MENU.getUrl()));
    }
    public void planButtonClicked(ActionEvent event)
    {
        logger.info("Button " + ((Button)event.getSource()).getText() + " is clicked");
        fadeNodeControl.changeSceneWithFade(informationAnchorPane, (Node) springFxmlLoader.load(ScenesInApplication.SCHEDULE_TABLE_NOW.getUrl()));
    }

    /**
     * Get information panel
     * */

    public AnchorPane getInformationAnchorPane()
    {
        return informationAnchorPane;
    }

}
