package ru.eleron.osa.lris.schedule.database.entities;

/**
 * This enum class for mark system for receive mark for exercises
 * A - 1
 * B - 0.75
 * C - 0.5
 * D - 0.25
 * F - 0
 * @author Reyzor
 * @version 1.0
 * @since 30.04.2018
 * */

public enum MarkForTask
{

    MARK_A(1f),
    MARK_B(0.75f),
    MARK_C(0.5f),
    MARK_D(0.25f),
    MARK_F(0f);

    private Float mark;

    MarkForTask(){}

    MarkForTask(Float mark)
    {
        this.mark = mark;
    }

    public Float getMark()
    {
        return mark;
    }
    public static MarkForTask getMark(Float mark)
    {
        switch(Float.floatToIntBits(mark*100)) {
            case 100: return MARK_A;
            case 75: return MARK_B;
            case 50: return MARK_C;
            case 25: return MARK_D;
            case 0: return MARK_F;
            default: return null;
        }
    }
}
