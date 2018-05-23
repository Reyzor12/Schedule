package ru.eleron.osa.lris.schedule.utils.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Messages for Users
 * @author reyzor
 * @version 1.0
 * @since 18.05.2018
 */
@Component
public class MessageUtils
{
    private ButtonType agree;

    public void showInfoMessage(String text)
    {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setHeaderText(text);
        dialog.setContentText(null);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
    public Optional<ButtonType> showOptionMessage(String title, String text, String buttonText)
    {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(text);
        dialog.setContentText(null);
        agree = new ButtonType(buttonText);
        ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(agree, cancel);
        return dialog.showAndWait();
    }
    public ButtonType getButtonTypeAgree()
    {
        return agree;
    }
}
