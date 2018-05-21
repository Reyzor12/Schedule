package ru.eleron.osa.lris.schedule.logic.scheduler;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.controllers.StartTask;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;

/**
 * @author reyzor
 * @version 1.0
 * @since 21.05.2018
 */
@Component
public class TaskLoad implements Runnable
{
    @Autowired
    private BaseSceneLoader sceneLoader;

    private CompositeTask compositeTask;

    public TaskLoad()
    {

    }

    public CompositeTask getCompositeTask() {
        return compositeTask;
    }

    public void setCompositeTask(CompositeTask compositeTask) {
        this.compositeTask = compositeTask;
    }

    @Override
    public void run()
    {
        Platform.runLater(() ->
        {
            sceneLoader.loadScene(ScenesInApplication.START_TASK.getUrl(), compositeTask, new Stage());
        });
    }

}
