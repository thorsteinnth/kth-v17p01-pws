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

    public String getSAWSDLModelReferenceRightSideOfHashtag()
    {
        // SAWSDLModelReference looks like this: sawsdl:modelReference="http://127.0.0.1/ontology/concept.owl#Price
        // We want the stuff that is on the right side of the hashtag

        if (this.SAWSDLModelReference == null || this.SAWSDLModelReference.equals(""))
            return null;

        String[] SAWSDLModelReferenceSplit = this.SAWSDLModelReference.split("#");
        if (SAWSDLModelReferenceSplit.length == 1)
        {
            // No hash tag was found
            return null;
        }
        else
        {
            // We assume that the stuff we are looking for is on the right side of the last hashtag in the string
            // (there should only be one hashtag in the string though)
            return SAWSDLModelReferenceSplit[SAWSDLModelReferenceSplit.length-1];
        }
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
