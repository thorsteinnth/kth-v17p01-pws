package common;

import java.util.ArrayList;
import java.util.List;

public class ElementContainer
{
    public String name;
    public List<String> subelements;

    public ElementContainer(String name)
    {
        this.name = name;
        this.subelements = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "ElementContainer{" +
                "name='" + name + '\'' +
                ", subelements=" + subelements +
                '}';
    }
}
