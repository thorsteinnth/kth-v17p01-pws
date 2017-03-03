package common;

import java.util.List;

public class ServiceContainer {

    public String name;
    public List<PortTypeContainer> portTypeContainers;

    public ServiceContainer()
    {
    }

    @Override
    public String toString() {
        return "ServiceContainer{" +
                "name='" + name + '\'' +
                ", portTypeContainers=" + portTypeContainers +
                '}';
    }
}
