package ru.eleron.osa.lris.schedule.utils;

import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.eleron.osa.lris.schedule.configurations.SpringContextConfiguration;

import java.io.IOException;

/**
 * Load the scene and inject beans into controller of the scene
 * Main method load(String) to load
 *
 * @author Reyzor
 * @vesion 1.0
 * @since 01.05.2018
 * */

public class SpringFxmlLoader {

    private static final Logger logger = LogManager.getLogger(SpringFxmlLoader.class);

    private static final AnnotationConfigApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(SpringContextConfiguration.class);

    public SpringFxmlLoader() {
        APPLICATION_CONTEXT.refresh();
    }

    public Object load(String url) {
        logger.info("try load scene " + url);
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz -> APPLICATION_CONTEXT.getBean(clazz));
        loader.setLocation(getClass().getClassLoader().getResource(url));
        try {
            return loader.load();
        } catch (IOException exception) {
            logger.error("scene " + url +  " don't load", exception);
        }
        return null;
    }
}
