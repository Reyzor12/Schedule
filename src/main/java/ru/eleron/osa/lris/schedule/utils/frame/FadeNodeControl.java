package ru.eleron.osa.lris.schedule.utils.frame;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.aop.Logging;

/**
 * Class for fade in application
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

@Component
public class FadeNodeControl
{
    private final static Integer DEFAULT_DURATION = 1000;
    private FadeTransition fadeTransition;

    public FadeNodeControl()
    {
        fadeTransition = new FadeTransition();
    }

    @Logging
    public void changeSceneWithFade(Pane container, Node filler)
    {
        changeSceneWithFade(container, filler, DEFAULT_DURATION);
    }

    @Logging
    public void changeSceneWithFade(Pane container, Node filler, Integer duration)
    {
        if (container != null && filler != null && duration != null && duration > 0)
        {
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setNode(container);
            fadeTransition.setDuration(Duration.millis(duration));
            fadeTransition.setOnFinished(event -> loadNewScene(container, filler));
            fadeTransition.play();
        }
    }

    private void loadNewScene(Pane container, Node filler)
    {
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(null);
        container.getChildren().clear();
        container.getChildren().add(filler);
        fadeTransition.play();
    }

    @Logging
    public void loadSceneInPane(Pane pane, Node node) {
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }

}
