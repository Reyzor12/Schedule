package ru.eleron.osa.lris.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.BaseSceneLoader;

@Component
public class MainMenuController {

    @Autowired
    private BaseSceneLoader sceneLoader;

    public void initialize() {
        System.out.println("it's my life");
    }

    public void onClick() {
        sceneLoader.loadScene("frame/MainMenu.fxml", 900, 1000);
    }
}
