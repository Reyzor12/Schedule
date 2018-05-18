package ru.eleron.osa.lris.schedule.utils.message;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.springframework.stereotype.Component;

/**
 * Messages for Users
 * @author reyzor
 * @version 1.0
 * @since 18.05.2018
 */
@Component
public class MessageUtils
{
    public void showInfoMessage(String text)
    {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setHeaderText(text);
        dialog.setContentText(null);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
