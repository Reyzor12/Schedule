package ru.eleron.osa.lris.schedule.controllers;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;

/**
 * Controller for view statistics of tasks
 * @author Reyzor
 * @version 1.0
 * @since 12.05.2018
 */

@Component
public class StatisticMenuController implements FrameControllerBaseIF
{
    public void initialize()
    {
        initData();
        configureElements();
        enableTooltips();
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
}
