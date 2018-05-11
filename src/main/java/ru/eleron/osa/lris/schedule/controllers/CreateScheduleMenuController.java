package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

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

    @FXML
    private Button leftRemoveButton;
    @FXML
    private Button rightAddButton;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView rightImage;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData() {
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements() {
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {

    }

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
}
