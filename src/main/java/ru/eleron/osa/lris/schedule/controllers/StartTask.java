package ru.eleron.osa.lris.schedule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.database.entities.CompositeTask;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseDelegeteIF;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

/**
 * Controller for information on start task
 * @author reyzor
 * @version 1.0
 * @since 21.05.2018
 */
@Component
public class StartTask implements FrameControllerBaseDelegeteIF
{
    @FXML
    private Button button;

    private CompositeTask compositeTask;

    public void initialize()
    {
        System.out.println("Hello world");
    }

    @Override
    public void initData() {

    }

    @Override
    public void configureElements() {

    }

    @Override
    public void enableTooltips() {

    }

    public void delegete(Object compositeTask) {
        this.compositeTask = (CompositeTask) compositeTask;
    }

    public void click() {
        System.out.println("HEY " + compositeTask);
    }
}
