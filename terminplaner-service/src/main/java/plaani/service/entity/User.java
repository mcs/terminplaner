package plaani.service.entity;

import javax.enterprise.inject.Alternative;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByAuthToken", query = "SELECT u FROM User u WHERE u.authKey = :authToken")
})
@Alternative
public class User extends AbstractEntity {

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = "\\w+@\\w+")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @Size(min = 1)
    private String username;

    @Size(min = 1)
    private String password;

    private String authKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public String toString() {
        return "User " + id;
    }
}
