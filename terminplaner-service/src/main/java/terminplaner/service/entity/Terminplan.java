package terminplaner.service.entity;

import javax.faces.event.PreRenderComponentEvent;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NamedQuery(name = "Teminplan.findByUuid", query = "SELECT t from Terminplan t WHERE t.uuid = :uuid")
public class Terminplan implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String name;

    @PrePersist
    public void initUuid() {
        uuid = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Plan %s: %s", uuid, name);
    }
}
