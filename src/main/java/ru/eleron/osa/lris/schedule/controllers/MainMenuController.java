package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.ProxyCompositeTask;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

/**
 * Class controller for main menu of application
 * @author reyzor
 * @version 1.0
 * @since 04.05.2018
 * */

@Component
public class MainMenuController {

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

    @Autowired
    private BaseSceneLoader sceneLoader;

    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
        System.out.println("it's my life");
    }

    private void configureElements()
    {
        taskTableView.setPlaceholder(new Label(ConstantsForElements.EMPTY_CURRENT_TASK_TABLE.getMessage()));
    }

    private void initData()
    {

    }

    private void enableTooltips()
    {

    }

    public void onClick()
    {
        sceneLoader.loadScene("frame/MainMenu.fxml", 900, 1000);
    }
}
