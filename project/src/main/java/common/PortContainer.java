package common;

public class PortContainer
{
    public String name;
    public PortTypeContainer portTypeContainer;

    public PortContainer(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "PortContainer{" +
                "name='" + name + '\'' +
                ", portTypeContainer=" + portTypeContainer +
                '}';
    }
}
