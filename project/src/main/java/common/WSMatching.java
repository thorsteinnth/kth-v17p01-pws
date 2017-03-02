package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class WSMatching {

    private ArrayList<Matching> matching;

    public WSMatching() {
    }

    public ArrayList<Matching> getMatching() {
        return matching;
    }

    @XmlElement(name="Matching")
    public void setMatching(ArrayList<Matching> matching) {
        this.matching = matching;
    }

    @Override
    public String toString() {
        return "WSMatching{" +
                "matching=" + matching +
                '}';
    }
}
