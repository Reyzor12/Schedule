package ru.eleron.osa.lris.schedule.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.aop.Logging;
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

    @Autowired
    private BaseSceneLoader sceneLoader;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private FadeNodeControl fadeNodeControl;

    //@Logging
    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    public void configureElements()
    {
        informationAnchorPane.getChildren().clear();
        informationAnchorPane.getChildren().add((Parent) springFxmlLoader.load(ScenesInApplication.SCHEDULE_TABLE_NOW.getUrl()));
    }

    public void initData()
    {

    }

    public void enableTooltips()
    {

    }

    public void onClick(ActionEvent event)
    {
        logger.info("Button " + event.getSource().getClass().getSimpleName() + " is clicked");
        fadeNodeControl.changeSceneWithFade(informationAnchorPane, (Node) springFxmlLoader.load("frame/Test.fxml"));
    }
}
