package ru.eleron.osa.lris.schedule.database.entities;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class EntityPrototype implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public EntityPrototype() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
