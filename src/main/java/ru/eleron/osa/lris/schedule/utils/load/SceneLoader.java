package ru.eleron.osa.lris.schedule.utils.load;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsSittings;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for load scene on stage from cache
 * @author Reyzor
 * @version 1.0
 * @since 01.05.2018
 * */

@Component
public class SceneLoader implements BaseSceneLoader
{

    /**
     * Default sittings
     * */

    private final static String TITLE = ConstantsSittings.APPLICATION_TITLE.getStringConstant();
    private final static String ICON_PATH = ConstantsSittings.APPLICATION_ICON_PATH.getStringConstant();
    private final static Integer MIN_WIDTH = ConstantsSittings.APPLICATION_MAIN_WINDOW_MIN_WIDTH.getIntegerConstant();
    private final static Integer MIN_HEIGHT = ConstantsSittings.APPLICATION_MAIN_WINDOW_MIN_HEIGHT.getIntegerConstant();
    private final static Integer MAX_WIDTH = ConstantsSittings.APPLICATION_MAIN_WINDOW_MAX_WIDTH.getIntegerConstant();
    private final static Integer MAX_HEIGHT = ConstantsSittings.APPLICATION_MAIN_WINDOW_MAX_HEIGHT.getIntegerConstant();
    private final static Integer DEFAULT_WIDTH = ConstantsSittings.APPLICATION_MAIN_WINDOW_DEFAULT_WIDTH.getIntegerConstant();
    private final static Integer DEFAULT_HEIGHT = ConstantsSittings.APPLICATION_MAIN_WINDOW_DEFAULT_HEIGHT.getIntegerConstant();

    private final static Logger logger = LogManager.getLogger(SceneLoader.class);

    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    private Map<String, Stage> sceneCache;

    public SceneLoader()
    {
        sceneCache = new HashMap<>();
    }

    /**
     * Scene loaders with different initial sittings
     * */

    @Override
    public void loadScene(String url, Integer width, Integer height)
    {
        Stage stage = sceneCache.get(url);
        if (stage == null) {
            logger.info("load scene absent in cache " + url);
            stage = getNewDefaultStage(url);
            stage.setWidth(width < MAX_WIDTH && width > MIN_WIDTH ? width : DEFAULT_WIDTH);
            stage.setHeight(height < MAX_HEIGHT && height > MIN_HEIGHT ? height : DEFAULT_HEIGHT);
            sceneCache.put(url, stage);
            stage.show();
        } else {
            if (!stage.isShowing())
            {
                logger.info("load scene from cache " + url);
                stage.show();
            }
        }
    }

    @Override
    public void loadScene(String url)
    {
        Stage stage = sceneCache.get(url);
        if (stage == null)
        {
            logger.info("load scene absent in cache " + url);
            stage = getNewDefaultStage(url);
            sceneCache.put(url, stage);
            stage.show();
        } else {
            if (!stage.isShowing())
            {
                logger.info("load scene from cache " + url);
                stage.show();
            }
        }
    }

    @Override
    public void loadScene(String url, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Integer width, Integer height)
    {
        Stage stage = sceneCache.get(url);
        if (stage == null)
        {
            logger.info("load scene absent in cache " + url);
            stage = getNewStage(url, minWidth, minHeight, maxWidth, maxHeight, width, height);
            sceneCache.put(url, stage);
            stage.show();
        } else {
            if (!stage.isShowing())
            {
                logger.info("load scene from cache " + url);
                stage.show();
            }
        }
    }

    @Override
    public void loadScene(String url, Stage stage)
    {
        if (stage != null && url != null)
        {
            sceneCache.put(url, stage);
            loadScene(url);
        }
    }

    @Override
    public void loadScene(String url, Object object)
    {
        Stage stage = sceneCache.get(url);
        if (stage == null)
        {
            logger.info("load scene absent in cache " + url);
            stage = getNewDefaultStage(url, object);
            sceneCache.put(url, stage);
            stage.show();
        } else {
            if (!stage.isShowing())
            {
                logger.info("load scene from cache " + url);
                stage.show();
            }
        }
    }

    @Override
    public void loadScene(String url, Object object, Stage stage)
    {
        if (stage != null && url != null)
        {
            sceneCache.put(url, setUpStage(url, object, stage));
            loadScene(url, object);
        }
    }

    @Override
    public void loadSceneWithoutCache(String url, Object object, Stage stage) {
        setUpStage(url, object, stage).show();
    }


    /**
     * utilities
     * */

    private void initDefaultStage(Stage stage)
    {
        stage.setHeight(DEFAULT_HEIGHT);
        stage.setWidth(DEFAULT_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMaxWidth(MAX_WIDTH);
        stage.setResizable(true);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
    }

    private void initStage(Stage stage, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Integer width, Integer height)
    {
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);
        stage.setMaxHeight(maxHeight);
        stage.setMaxWidth(maxWidth);
        stage.setResizable(true);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
    }

    private Stage getNewDefaultStage(String url)
    {
        final Stage stage = createStageWithoutSittings(url);
        initDefaultStage(stage);
        return stage;
    }

    private Stage getNewStage(String url, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Integer width, Integer height)
    {
        final Stage stage = createStageWithoutSittings(url);
        initStage(stage, minWidth, minHeight, maxWidth, maxHeight, width, height);
        return stage;
    }

    private Stage getNewDefaultStage(String url, Object object)
    {
        final Stage stage = createStageWithoutSittingsWithDelegete(url, object);
        initDefaultStage(stage);
        return stage;
    }

    private Stage getNewStage(String url, Object object, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Integer width, Integer height)
    {
        final Stage stage = createStageWithoutSittingsWithDelegete(url, object);
        initStage(stage, minWidth, minHeight, maxWidth, maxHeight, width, height);
        return stage;
    }

    private Stage createStageWithoutSittings(String url)
    {
        Parent root = (Parent) springFxmlLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
    private Stage createStageWithoutSittingsWithDelegete(String url, Object object)
    {
        Parent root = (Parent) springFxmlLoader.load(url, object);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    private Stage setUpStage(String url, Stage stage)
    {
        Parent root = (Parent) springFxmlLoader.load(url);
        stage.setScene(new Scene(root));
        return stage;
    }

    private Stage setUpStage(String url, Object object, Stage stage)
    {
        stage.setScene(new Scene((Parent) springFxmlLoader.load(url, object)));
        return stage;
    }
}
