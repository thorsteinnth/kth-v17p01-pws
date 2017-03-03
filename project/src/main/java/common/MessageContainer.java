package common;

import java.util.ArrayList;
import java.util.List;

public class MessageContainer
{
    public String name;
    public List<ElementContainer> elements;

    public MessageContainer(String name)
    {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "MessageContainer{" +
                "name='" + name + '\'' +
                ", elements=" + elements +
                '}';
    }
}
