package ru.eleron.osa.lris.schedule.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

/**
 * Controller for fxml TaskCreateUpdateMenu
 * base functional is create of update tasks
 * @author reyzor
 * @version 1.0
 * @since 10.05.2018
 */

@Component
public class TaskCreateUpdateMenuController implements FrameControllerBaseIF {

    private static final Logger logger = LogManager.getLogger(TaskCreateUpdateMenuController.class);

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
}
