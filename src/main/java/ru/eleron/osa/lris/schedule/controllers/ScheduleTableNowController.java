package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

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
    private TableView<ProxyCompositeTask> taskTableView;
    @FXML
    private TableColumn<ProxyCompositeTask, String> taskNameColumn;
    @FXML
    private TableColumn<ProxyCompositeTask, String> taskStartColumn;
    @FXML
    private TableColumn<ProxyCompositeTask, String> taskEndColumn;
    @FXML
    private TableColumn<ProxyCompositeTask, String> taskMarkColumn;

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
    public void configureElements()
    {
        taskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_CURRENT_TASK_TABLE.getMessage()));
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {

    }
}
