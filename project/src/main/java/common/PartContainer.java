package common;

import java.util.ArrayList;
import java.util.List;

public class PartContainer
{
    // A part can either contain an element (complex type) or just a type

    public String name;
    public String type;
    public String elementName;
    public List<TypeNameTuple> subelements;
    public String SAWSDLModelReference;

    public PartContainer(String name)
    {
        this.name = name;
        this.type = null;
        this.elementName = null;
        this.subelements = new ArrayList<>();
        this.SAWSDLModelReference = null;
    }

    @Override
    public String toString()
    {
        return "PartContainer{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", elementName='" + elementName + '\'' +
                ", subelements=" + subelements +
                ", SAWSDLModelReference='" + SAWSDLModelReference + '\'' +
                '}';
    }
}
