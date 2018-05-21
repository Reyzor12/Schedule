package ru.eleron.osa.lris.schedule.logic.scheduler;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.controllers.StartTask;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.load.SpringFxmlLoader;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsSittings;

/**
 * @author reyzor
 * @version 1.0
 * @since 21.05.2018
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
            sceneLoader.loadSceneWithoutCache(ScenesInApplication.START_TASK.getUrl(), compositeTask, configureStage(new Stage()));
        });
    }

    private Stage configureStage(Stage stage)
    {
        stage.setTitle(ConstantsSittings.APPLICATION_TITLE.getStringConstant());
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ConstantsSittings.APPLICATION_ICON_PATH.getStringConstant())));
        return stage;
    }

}
