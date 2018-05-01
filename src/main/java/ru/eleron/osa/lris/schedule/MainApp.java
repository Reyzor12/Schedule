package ru.eleron.osa.lris.schedule;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.eleron.osa.lris.schedule.configurations.JpaConfigurations;
import ru.eleron.osa.lris.schedule.configurations.SpringContextConfiguration;

public class MainApp extends Application  {

    private final static Logger logger = LogManager.getLogger();

    @Autowired
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        initApp();
        System.out.println("Hello World");
    }

    public void stop() {
        sessionFactory.close();
    }

    public void initApp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContextConfiguration.class);
        logger.info("spring context init");
        initMainWindow();
        logger.info("init main window");
    }

    public void initMainWindow() {
        Parent parent = null;
    }

}
