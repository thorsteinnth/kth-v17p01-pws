package common;

public class TypeNameTuple
{
    public String type;
    public String name;

    public TypeNameTuple(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "TypeNameTuple{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
