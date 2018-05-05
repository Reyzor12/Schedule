package ru.eleron.osa.lris.schedule.utils.frame;

/**
 * This class contains all relative addresses of fxml files
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

public enum ScenesInApplication
{
    MAIN_MENU("frame/MainMenu.fxml"),
    SCHEDULE_TABLE_NOW("frame/ScheduleTableNow.fxml");

    private String url;

    ScenesInApplication(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
