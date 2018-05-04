package ru.eleron.osa.lris.schedule.utils.load;

import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.eleron.osa.lris.schedule.MainApp;

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

    public SpringFxmlLoader() {
    }

    public Object load(String url) {
        logger.info("try load scene " + url);
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz -> MainApp.APPLICATION_CONTEXT.getBean(clazz));
        loader.setLocation(getClass().getClassLoader().getResource(url));
        try {
            return loader.load();
        } catch (IOException exception) {
            logger.error("scene " + url +  " don't load", exception);
        }
        return null;
    }
}
