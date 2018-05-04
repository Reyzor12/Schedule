package ru.eleron.osa.lris.schedule.utils.storage;

/**
 * Class is bundle for common constants of application and common sittings
 * @author reyzor
 * @version 1.0
 * @since 04.05.2018
 */

public enum ConstantsSittings {
    /**
     * Application common sittings
     * */
    APPLICATION_TITLE("ПЛАНИРОВЩИК ДЕЛ"),
    APPLICATION_ICON_PATH("/images/icons/SceduleIcon.png"),
    /**
     * Size of application windows
     */
    APPLICATION_MAIN_WINDOW_MIN_WIDTH(1024),
    APPLICATION_MAIN_WINDOW_MIN_HEIGHT(720),
    APPLICATION_MAIN_WINDOW_MAX_WIDTH(1920),
    APPLICATION_MAIN_WINDOW_MAX_HEIGHT(1080),
    APPLICATION_MAIN_WINDOW_DEFAULT_WIDTH(1024),
    APPLICATION_MAIN_WINDOW_DEFAULT_HEIGHT(720);

    private String stringConstant;
    private Integer integerConstant;

    ConstantsSittings(String stringConstant)
    {
        this.stringConstant = stringConstant;
    }

    ConstantsSittings(Integer integerConstant)
    {
        this.integerConstant = integerConstant;
    }

    public String getStringConstant()
    {
        return stringConstant;
    }

    public Integer getIntegerConstant()
    {
        return integerConstant;
    }
}
