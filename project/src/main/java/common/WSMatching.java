package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class WSMatching {

    private List<Matching> matching;

    public WSMatching() {
    }

    public List<Matching> getMatching() {
        return matching;
    }

    @XmlElement(name="Matching")
    public void setMatching(List<Matching> matching) {
        this.matching = matching;
    }

    @Override
    public String toString() {
        return "WSMatching{" +
                "matching=" + matching +
                '}';
    }
}
