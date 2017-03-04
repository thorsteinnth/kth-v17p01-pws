package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "WSMatching")
public class WSMatching {

    private List<Matching> matching;

    public WSMatching()
    {
        this.matching = new ArrayList<>();
    }

    public List<Matching> getMatching()
    {
        return matching;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name="Matching")
    public void setMatching(List<Matching> matching)
    {
        this.matching = matching;
    }

    @Override
    public String toString()
    {
        return "WSMatching{" +
                "matching=" + matching +
                '}';
    }
}
