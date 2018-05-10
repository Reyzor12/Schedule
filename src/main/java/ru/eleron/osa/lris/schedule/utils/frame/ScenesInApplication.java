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
    SCHEDULE_TABLE_NOW("frame/ScheduleTableNow.fxml"),
    CREATE_SCHEDULE_TEMPLATE("frame/CreateScheduleTemplate.fxml"),
    CREATE_SCHEDULE_MENU("frame/CreateScheduleMenu.fxml"),
    TASK_MANAGER_MENU("frame/TaskManagerMenu.fxml"),
    TASK_CREATE_UPDATE_MENU("frame/TaskCreateUpdateMenu.fxml"),
    CHOOSE_PLAN_FOR_DAY("frame/ChoosePlanForDay.fxml");

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
