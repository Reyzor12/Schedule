package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.*;

/**
 * StatisticClass is class for statistic. It contains link with ProxyCompositeTask
 * and CompositeTask (which contains in ProxyCompositeTask) and MarkForTask for this task in this proxy;
 * @author Reyzor
 * @version 1.0
 * @since 30.04.2018
 */

@Entity
@Table(name = "statistic_class")
public class StatisticClass {

    @EmbeddedId
    private CompositeTaskProxyCompositeKey compositeKey;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mark")
    private MarkForTask mark;

    public StatisticClass(){}
    public StatisticClass(CompositeTaskProxyCompositeKey compositeKey, MarkForTask mark) {
        this.compositeKey = compositeKey;
        this.mark = mark;
    }
    public StatisticClass(CompositeTask compositeTask, ProxyCompositeTask proxyCompositeTask, MarkForTask mark) {
        this.compositeKey = new CompositeTaskProxyCompositeKey(compositeTask, proxyCompositeTask);
        this.mark = mark;
    }

    public CompositeTaskProxyCompositeKey getCompositeKey() {
        return compositeKey;
    }

    public void setCompositeKey(CompositeTaskProxyCompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }

    public MarkForTask getMark() {
        return mark;
    }

    public void setMark(MarkForTask mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "StatisticClass{" +
                "compositeKey=" + compositeKey +
                ", mark=" + mark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticClass that = (StatisticClass) o;

        if (compositeKey != null ? !compositeKey.equals(that.compositeKey) : that.compositeKey != null) return false;
        return mark == that.mark;
    }

    @Override
    public int hashCode() {
        int result = compositeKey != null ? compositeKey.hashCode() : 0;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }
}
