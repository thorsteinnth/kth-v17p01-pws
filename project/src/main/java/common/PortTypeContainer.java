package common;

import java.util.ArrayList;
import java.util.List;

public class PortTypeContainer
{
    public String name;
    public List<OperationContainer> operations;

    public PortTypeContainer(String name)
    {
        this.name = name;
        this.operations = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "PortTypeContainer{" +
                "name='" + name + '\'' +
                ", operations=" + operations +
                '}';
    }
}
