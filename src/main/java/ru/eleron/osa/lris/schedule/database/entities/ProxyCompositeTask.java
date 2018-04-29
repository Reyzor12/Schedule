package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "proxy_composite_task")
public class ProxyCompositeTask extends EntityPrototype {
    @Column(name = "start")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "entity")
    private CompositeTask compositeTask;

    public ProxyCompositeTask() {}
    public ProxyCompositeTask(Date date, CompositeTask compositeTask) {
        this.date = date;
        this.compositeTask = compositeTask;
    }

    public List<String> generateStatistic(CompositeTask composite) {
        if (compositeTask == null || compositeTask.getChildren() == null || compositeTask.getChildren().isEmpty() ) return  new ArrayList<>();
        return composite.getChildren().stream().map(compositeStream -> compositeStream.getName() + "=0").collect(Collectors.toList());
    }

    public LocalDateTime getDate() {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setDate(LocalDateTime localDateTime) {
        this.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCompositeTask(CompositeTask compositeTask) {
        this.compositeTask = compositeTask;
    }
    public CompositeTask getCompositeTask() {
        return compositeTask;
    }



    @Override
    public String toString() {
        return "ProxyCompositeTask{" +
                "date=" + date +
                ", compositeTask=" + compositeTask.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyCompositeTask that = (ProxyCompositeTask) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return compositeTask != null ? compositeTask.equals(that.compositeTask) : that.compositeTask == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (compositeTask != null ? compositeTask.hashCode() : 0);
        return result;
    }
}
