package plaani.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "Terminplan.findByUser", query = "select t from Terminplan t where t.user = :user")
})
public class Terminplan extends AbstractEntity {

    @Id
    private String id;

    private String summary;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private User user;

    @PrePersist
    public void initId() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Plan %s: %s", id, summary);
    }
}
