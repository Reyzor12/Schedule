package ru.eleron.osa.lris.schedule.utils.load;

import javafx.stage.Stage;

/**
 * Base interface for load scene
 * @author Reyzor
 * @version 1.0
 * @since 01.05.2018
 * */

public interface BaseSceneLoader
{

    void loadScene(String url, Integer width, Integer height);
    void loadScene(String url);
    void loadScene(String url, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Integer width, Integer height);
    void loadScene(String url, Stage stage);
    void loadScene(String url, Object object);
    void loadScene(String url, Object object, Stage stage);
    void loadSceneWithoutCache(String url, Object object, Stage stage);
}
