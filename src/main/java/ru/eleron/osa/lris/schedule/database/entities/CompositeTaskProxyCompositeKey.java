package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CompositeTaskProxyCompositeKey implements Serializable{

    @ManyToOne
    @JoinColumn(name = "proxy_composite_task_id")
    private ProxyCompositeTask proxyCompositeTask;
    @ManyToOne
    @JoinColumn(name = "composite_task_id")
    private CompositeTask compositeTask;
}
