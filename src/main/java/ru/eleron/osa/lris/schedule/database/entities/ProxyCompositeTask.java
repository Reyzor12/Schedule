package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "proxy_composite_task")
public class ProxyCompositeTask extends EntityPrototype {
    @Column(name = "start")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn
    private CompositeTask compositeTask;

    public ProxyCompositeTask() {}
    public ProxyCompositeTask(Date date, CompositeTask compositeTask) {
        this.date = date;
        this.compositeTask = compositeTask;
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

    
}
