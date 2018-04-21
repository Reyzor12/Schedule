package ru.eleron.osa.lris.schedule.database.patterns;

public interface CompositePatternForTask {
    String getName();
    void setName(String name);
    Integer getScore();
    void setScore(Integer score);
    Integer getTime();
    void setTime(Integer time);
}
