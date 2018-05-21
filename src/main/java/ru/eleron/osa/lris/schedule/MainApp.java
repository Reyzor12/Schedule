package ru.eleron.osa.lris.schedule;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.eleron.osa.lris.schedule.configurations.SpringContextConfiguration;
import ru.eleron.osa.lris.schedule.utils.frame.ScenesInApplication;
import ru.eleron.osa.lris.schedule.utils.load.BaseSceneLoader;
import ru.eleron.osa.lris.schedule.utils.load.SceneLoader;

public class MainApp extends Application
{

    private final static Logger logger = LogManager.getLogger(MainApp.class);
    public final static AnnotationConfigApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(SpringContextConfiguration.class);

    private BaseSceneLoader sceneLoader;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        initApp();
    }

    public void initApp()
    {
        logger.info("spring context init");
        initMainWindow();
        logger.info("init main window");
    }

    public void initMainWindow()
    {
        sceneLoader = (SceneLoader)APPLICATION_CONTEXT.getBean("sceneLoader");
        sceneLoader.loadScene(ScenesInApplication.MAIN_MENU.getUrl());
    }

    public void stop()
    {
        APPLICATION_CONTEXT.stop();
        System.exit(0);
    }
}
