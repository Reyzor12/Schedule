package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CompositeTaskProxyCompositeKey implements Serializable
{

    @ManyToOne
    @JoinColumn(name = "proxy_composite_task_id")
    private ProxyCompositeTask proxyCompositeTask;
    @ManyToOne
    @JoinColumn(name = "composite_task_id")
    private CompositeTask compositeTask;

    public CompositeTaskProxyCompositeKey(){}
    public CompositeTaskProxyCompositeKey(CompositeTask compositeTask, ProxyCompositeTask proxyCompositeTask)
    {
        this.compositeTask = compositeTask;
        this.proxyCompositeTask = proxyCompositeTask;
    }

    public ProxyCompositeTask getProxyCompositeTask()
    {
        return proxyCompositeTask;
    }

    public void setProxyCompositeTask(ProxyCompositeTask proxyCompositeTask)
    {
        this.proxyCompositeTask = proxyCompositeTask;
    }

    public CompositeTask getCompositeTask()
    {
        return compositeTask;
    }

    public void setCompositeTask(CompositeTask compositeTask)
    {
        this.compositeTask = compositeTask;
    }

    @Override
    public String toString()
    {
        return "CompositeTaskProxyCompositeKey{" +
                "proxyCompositeTask=" + proxyCompositeTask +
                ", compositeTask=" + compositeTask +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeTaskProxyCompositeKey that = (CompositeTaskProxyCompositeKey) o;

        if (proxyCompositeTask != null ? !proxyCompositeTask.equals(that.proxyCompositeTask) : that.proxyCompositeTask != null)
            return false;
        return compositeTask != null ? compositeTask.equals(that.compositeTask) : that.compositeTask == null;
    }

    @Override
    public int hashCode()
    {
        int result = proxyCompositeTask != null ? proxyCompositeTask.hashCode() : 0;
        result = 31 * result + (compositeTask != null ? compositeTask.hashCode() : 0);
        return result;
    }
}
