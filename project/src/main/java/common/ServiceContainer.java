package common;

import java.util.ArrayList;
import java.util.List;

public class ServiceContainer {

    public String name;
    public List<PortContainer> portContainers;

    public ServiceContainer(String name)
    {
        this.name = name;
        this.portContainers = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "ServiceContainer{" +
                "name='" + name + '\'' +
                ", portContainers=" + portContainers +
                '}';
    }
}
