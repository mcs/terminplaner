package plaani.service.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Transient
    protected final Map<String, String> links = new HashMap<>();

    public void addLink(String rel, String uri) {
        links.put(rel, uri);
    }

    public void removeLink(String rel) {
        links.remove(rel);
    }

    public Map<String, String> getLinks() {
        return links;
    }
}
