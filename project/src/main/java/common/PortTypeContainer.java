package common;

import java.util.ArrayList;
import java.util.List;

public class PortTypeContainer
{
    public String serviceName;
    public String portTypeName;
    public List<OperationContainer> operations;

    public PortTypeContainer(String serviceName, String portTypeName)
    {
        this.serviceName = serviceName;
        this.portTypeName = portTypeName;
        this.operations = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "PortTypeContainer{" +
                "serviceName='" + serviceName +'\'' +
                "portTypeName='" + portTypeName + '\'' +
                ", operations=" + operations +
                '}';
    }
}
