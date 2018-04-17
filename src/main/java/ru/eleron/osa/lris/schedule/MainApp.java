package ru.eleron.osa.lris.schedule;

import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MainApp extends Application  {

    @Autowired
    SessionFactory sessionFactory;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello world");
    }

    public void stop() {
        sessionFactory.close();
    }

}
