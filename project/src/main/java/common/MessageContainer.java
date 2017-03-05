package common;

import java.util.ArrayList;
import java.util.List;

public class MessageContainer
{
    public String name;
    public List<PartContainer> parts;

    public MessageContainer(String name)
    {
        this.name = name;
        this.parts = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "MessageContainer{" +
                "name='" + name + '\'' +
                ", parts=" + parts +
                '}';
    }
}
