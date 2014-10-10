package terminplaner.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@NamedQuery(name = "Teminplan.findByUuid", query = "SELECT t from Terminplan t WHERE t.uuid = :uuid")
public class Terminplan implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String summary;

    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String name) {
        this.summary = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Plan %s: %s", uuid, summary);
    }
}
