package plaani.service.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Invitation.findByTermin", query = "select i from Invitation i where i.termin = :termin")
public class Invitation {

    public static enum Answer {
        YES, NO, MAYBE
    }

    @Id
    private String id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Answer answer;

    @ManyToOne
    private Terminplan termin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Terminplan getTermin() {
        return termin;
    }

    public void setTermin(Terminplan termin) {
        this.termin = termin;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
