package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity like a task (concrete object). Main object of application
 * @author Reyzor
 * @version 1.0
 * @since 05.05.2018
 * */

@Entity
@Table(name = "proxy_composite_task")
public class ProxyCompositeTask extends EntityPrototype
{

    @Column(name = "name")
    private String name;
    @Column(name = "start")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "entity")
    private CompositeTask compositeTask;

    public ProxyCompositeTask() {}
    public ProxyCompositeTask(Date date, CompositeTask compositeTask, String name)
    {
        this.date = date;
        this.compositeTask = compositeTask;
        this.name = name;
    }

    public List<String> generateStatistic(CompositeTask composite)
    {
        if (compositeTask == null || compositeTask.getChildren() == null || compositeTask.getChildren().isEmpty() ) return  new ArrayList<>();
        return composite.getChildren().stream().map(compositeStream -> compositeStream.getName() + "=0").collect(Collectors.toList());
    }

    public LocalDateTime getDate()
    {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setDate(LocalDateTime localDateTime)
    {
        this.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setCompositeTask(CompositeTask compositeTask)
    {
        this.compositeTask = compositeTask;
    }
    public CompositeTask getCompositeTask()
    {
        return compositeTask;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ProxyCompositeTask{" +
                "date=" + date +
                ", compositeTask=" + compositeTask.getName() +
                ", name = " + name +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyCompositeTask that = (ProxyCompositeTask) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return compositeTask != null ? compositeTask.equals(that.compositeTask) : that.compositeTask == null;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (compositeTask != null ? compositeTask.hashCode() : 0);
        return result;
    }
}
