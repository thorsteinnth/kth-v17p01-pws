package common;

import com.predic8.schema.ComplexType;
import com.predic8.schema.Element;
import com.predic8.schema.Schema;
import com.predic8.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser
{
    private final static Logger LOG = LoggerFactory.getLogger(Parser.class);
    private boolean isSemanticParser;

    // XPATH stuff
    private DocumentBuilderFactory documentBuilderFactory;
    private Document document;
    private XPathFactory xPathFactory;

    public Parser(boolean isSemanticParser)
    {
        this.isSemanticParser = isSemanticParser;
    }

    //region Predic8 parsers

    public List<ServiceContainer> parseServices(File wsdl)
    {
        if (this.isSemanticParser)
        {
            try
            {
                LOG.info("Building document for use with XPATH for file: " + wsdl);
                this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
                this.documentBuilderFactory.setNamespaceAware(true);
                DocumentBuilder documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
                this.document = documentBuilder.parse(wsdl);
                this.xPathFactory = XPathFactory.newInstance();
            }
            catch (ParserConfigurationException|SAXException|IOException ex)
            {
                LOG.error("Unable to build document to use with XPATH: " + ex.toString());
            }
        }

        LOG.info("Parsing services for: " + wsdl);

        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdl.getAbsolutePath());

        List<ServiceContainer> serviceContainers = new ArrayList<>();

        for (Service service : defs.getServices())
        {
            ServiceContainer serviceContainer = new ServiceContainer(service.getName());
            serviceContainer.portContainers.addAll(parsePorts(service.getPorts()));
            serviceContainers.add(serviceContainer);
        }

        return serviceContainers;
    }

    private List<PortContainer> parsePorts(List<Port> ports)
    {
        /*
        https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_Fuse/6.0/html/Using_the_Web_Services_Bindings_and_Transports/files/FUSECXFBindingIntro.html
        To ensure that an endpoint defines only a single service, WSDL requires that a binding can only represent a single port type.
        For example, if you had a contract with two port types, you could not write a single binding that mapped both of them
        into a concrete data format. You would need two bindings.
        */

        List<PortContainer> portContainers = new ArrayList<>();

        for (Port port : ports)
        {
            PortContainer portContainer = new PortContainer(port.getName());

            PortType portType = port.getBinding().getPortType();
            PortTypeContainer portTypeContainer = new PortTypeContainer(portType.getName());

            for (Operation operation : portType.getOperations())
            {
                OperationContainer operationContainer = new OperationContainer(operation.getName());
                operationContainer.inputMessage = parseMessage(operation.getInput().getMessage());
                operationContainer.outputMessage = parseMessage(operation.getOutput().getMessage());

                portTypeContainer.operations.add(operationContainer);
            }

            portContainer.portTypeContainer = portTypeContainer;
            portContainers.add(portContainer);
        }

        return portContainers;
    }

    private MessageContainer parseMessage(Message message)
    {
        MessageContainer messageContainer = new MessageContainer(message.getName());

        for (Part part : message.getParts())
        {
            PartContainer partContainer = new PartContainer(part.getName());

            // Part can have an element that we need to dig further down to find, or just a type (e.g. string)
            Element partElement = part.getElement();

            if (partElement != null)
            {
                // This part has an element
                partContainer.elementName = partElement.getName();
                // TODO Not getting an embedded type for some part elements (e.g. addressGeocoder.wsdl),
                // but we still have a name and element (even though the element actually is a complex type, but predic8 is not finding it)
                // Just ignoring that for now
                if (partElement.getEmbeddedType() instanceof ComplexType)
                {
                    ComplexType partElementComplexType = (ComplexType) partElement.getEmbeddedType();

                    // Flatten all complex types below here to their basic types
                    partContainer.subelements.addAll(flattenComplexTypeRecursive(partElementComplexType));
                }
            }
            else
            {
                // Check if we have a type here instead
                // Let's just add the part itself as a subelement too to make comparison easier
                // (we always compare sub elements)

                if (part.getType() != null)
                {
                    String partTypeName = (part.getType().getName() != null ? part.getType().getName() : part.getTypePN().toString());
                    partContainer.type = partTypeName;
                    partContainer.subelements.add(new TypeNameTuple(partTypeName, part.getName()));
                }
                // Some files do not get a type object, but they do get a type prefixedname object (e.g. SAWSDL 1personbicycle4wheeledcar_price_service)
                else if (part.getTypePN() != null)
                {
                    String partTypeName = part.getTypePN().toString();
                    partContainer.type = partTypeName;
                    partContainer.subelements.add(new TypeNameTuple(partTypeName, part.getName()));
                }
            }

            if (this.isSemanticParser)
                partContainer.SAWSDLModelReference = getSAWSDLModelReference(partContainer);

            messageContainer.parts.add(partContainer);
        }

        return messageContainer;
    }

    //region XPATH stuff (to find SAWSDL modelReference)

    /**
     * Find the sawsdlModelReference attribute for the part in the partContainer. For use in semantic parser.
     * If the part container has a type, search for the model reference attribute for that type.
     * If the part container has an element, search for the model reference attribute for that element.
     * @param partContainer
     * @return The sawsdlModelReference attribute for the part
     */
    private String getSAWSDLModelReference(PartContainer partContainer)
    {
        // The part container should have either a type or an element name (complex type name)
        // Search for the model reference for whichever we have.

        String searchString;
        if (partContainer.type != null)
            searchString = partContainer.type;
        else if (partContainer.elementName != null)
            searchString = partContainer.elementName;
        else
            return null;

        XPath xpath = this.xPathFactory.newXPath();
        xpath.setNamespaceContext(getWsdlNamespaceContext());

        try
        {
            // Search for the element somewhere in the doc
            // Start by searching for complex type, then simple type
            // It seems there is no way we can tell if this is a simple type or a complex type beforehand
            XPathExpression elementExpr = xpath.compile("//xsd:complexType[@name='" + searchString + "']");
            Node foundNode = (Node) elementExpr.evaluate(this.document, XPathConstants.NODE);
            if (foundNode == null)
            {
                // Now let's try simpleType
                elementExpr = xpath.compile("//xsd:simpleType[@name='" + searchString + "']");
                foundNode = (Node) elementExpr.evaluate(this.document, XPathConstants.NODE);

                if (foundNode == null)
                {
                    // Assume that this is a simple type directly in the message part, check if the model reference is
                    // defined in the part
                    elementExpr = xpath.compile("//wsdl:part[@name='" + partContainer.name + "']");
                    foundNode = (Node) elementExpr.evaluate(this.document, XPathConstants.NODE);

                    if (foundNode == null)
                    {
                        LOG.debug("getSAWSDLModelReference - Could not find type definition for: " + searchString);
                        return null;
                    }
                }
            }

            Node sawsdlModelReferenceNode = foundNode.getAttributes().getNamedItem("sawsdl:modelReference");

            if (sawsdlModelReferenceNode == null)
            {
                LOG.debug("getSAWSDLModelReference - Could not find sawsdlModelReference attribute for search string: "
                        + searchString + " - Node: " + foundNode.toString());
                return null;
            }

            return sawsdlModelReferenceNode.getNodeValue();
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return null;
        }
    }

    private NamespaceContext getWsdlNamespaceContext()
    {
        return new NamespaceContext()
        {
            @Override
            public String getNamespaceURI(String prefix)
            {
                if (prefix.equals("xsd"))
                    return "http://www.w3.org/2001/XMLSchema";
                else if (prefix.equals("sawsdl"))
                    return "http://www.w3.org/ns/sawsdl";
                else if (prefix.equals("wsdl"))
                    return "http://schemas.xmlsoap.org/wsdl/";
                else
                    return null;
            }

            @Override
            public String getPrefix(String namespaceURI)
            {
                return null;
            }

            @Override
            public Iterator getPrefixes(String namespaceURI)
            {
                return null;
            }
        };
    }

    //endregion

    private List<TypeNameTuple> flattenComplexTypeRecursive(ComplexType complexType)
    {
        List<TypeNameTuple> typeNameTuples = new ArrayList<>();

        // TODO
        // Complex type may have a "sequence", or "all" or some others.
        // It doesn't look like the predic8 parser can handle anything other than sequence.
        // Just ignoring the type then (for now at least)
        // (e.g. in RegalTechnologiesDevConnectAPIProfile.wsdl)
        if (complexType.getSequence() == null)
            return typeNameTuples;

        for (Element sequenceElement : complexType.getSequence().getElements())
        {
            if (isElementComplexType(sequenceElement))
            {
                // This is a complex type
                ComplexType sequenceElementComplex = getComplexTypeByTypeName(sequenceElement.getSchema(),
                        sequenceElement.getTypeString(sequenceElement.getType()));

                typeNameTuples.addAll(flattenComplexTypeRecursive(sequenceElementComplex));
            }
            else
            {
                // Some elements in the given WSDLs do not have a type (e.g. TripAuthorityAPIProfile.xml)
                String typeName;
                if (sequenceElement.getType() != null)
                    typeName = sequenceElement.getTypeString(sequenceElement.getType());
                else
                    typeName = null;

                TypeNameTuple typeNameTuple = new TypeNameTuple(typeName, sequenceElement.getName());
                typeNameTuples.add(typeNameTuple);
            }
        }

        return typeNameTuples;
    }

    private ComplexType getComplexTypeByTypeName(Schema schema, String typeName)
    {
        // Complex type names in schema do not contain namespaces. Remove it if it is there.
        typeName = removeNamespace(typeName);
        ComplexType foundComplexType = schema.getComplexType(typeName);
        return foundComplexType;
    }

    private boolean isElementComplexType(Element element)
    {
        // Let's compare the name of the element with the complex types in the schema.
        // complexTypes.contains() does not work here

        // Some elements in the given WSDLs do not have a type (e.g. TripAuthorityAPIProfile.xml)
        if (element.getType() == null)
            return false;

        List<String> complexTypeNames = new ArrayList<>();
        for (ComplexType complexType : element.getSchema().getComplexTypes())
            complexTypeNames.add(complexType.getName());

        String elementTypeName = element.getTypeString(element.getType());
        // This element type name may contain a namespace, but the complex type names do not. Remove namespace
        elementTypeName = removeNamespace(elementTypeName);

        if (complexTypeNames.contains(elementTypeName))
            return true;

        return false;
    }

    private String removeNamespace(String name)
    {
        String[] split = name.split(":");
        if (split.length == 2)
            return split[1];
        else
            return name;
    }

    //endregion

    //region Homemade parsers - not in use
    /*

    private ServiceContainer parseService(File wsdl) throws SAXException
    {
        ServiceContainer serviceContainer = new ServiceContainer();

        try
        {
            DocumentBuilder builder = this.documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.parse(wsdl);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            XPathExpression getServiceNameExpr = xpath.compile("//wsdl:service");
            NodeList serviceNameNodes = (NodeList) getServiceNameExpr.evaluate(doc, XPathConstants.NODESET);
            String serviceName = "";

            for (int i = 0; i < serviceNameNodes.getLength(); i++) {
                Node node = serviceNameNodes.item(i);
                serviceName = node.getAttributes().getNamedItem("name").getNodeValue();

                if (!serviceName.equals(""))
                    break;
            }

            serviceContainer.name = serviceName;

            XPathExpression getPortTypesExptr = xpath.compile("//wsdl:portType");
            NodeList portTypeNodes = (NodeList) getPortTypesExptr.evaluate(doc, XPathConstants.NODESET);

            serviceContainer.portTypeContainers = parsePortTypes(doc, portTypeNodes);
        }
        catch (ParserConfigurationException|XPathExpressionException|IOException ex)
        {
            LOG.error(ex.toString());
        }
        finally
        {
            return serviceContainer;
        }
    }

    private List<PortTypeContainer> parsePortTypes(Document doc, NodeList portTypeNodeList)
    {
        try
        {
            List<PortTypeContainer> portTypeContainers = new ArrayList<>();

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            for (int i = 0; i < portTypeNodeList.getLength(); i++)
            {
                Node node = portTypeNodeList.item(i);
                String portTypeName = node.getAttributes().getNamedItem("name").getNodeValue();
                PortTypeContainer portTypeContainer = new PortTypeContainer(portTypeName);

                XPathExpression operationExpr = xpath.compile("wsdl:operation");
                NodeList operationNodeList = (NodeList) operationExpr.evaluate(node, XPathConstants.NODESET);

                portTypeContainer.operations = parseOperations(doc, operationNodeList);

                portTypeContainers.add(portTypeContainer);
            }

            return portTypeContainers;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return new ArrayList<>();
        }
    }

    private List<OperationContainer> parseOperations(Document doc, NodeList operationNodeList)
    {
        try
        {
            List<OperationContainer> operationContainers = new ArrayList<>();

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            for (int i = 0; i < operationNodeList.getLength(); i++)
            {
                Node operationNode = operationNodeList.item(i);
                String operationName = operationNode.getAttributes().getNamedItem("name").getNodeValue();
                OperationContainer operationContainer = new OperationContainer(operationName);

                String operationInputMessageName = xpath.compile("wsdl:input/@message").evaluate(operationNode);
                operationContainer.inputMessage = parseMessage(doc, operationInputMessageName);

                String operationOutputMessageName = xpath.compile("wsdl:output/@message").evaluate(operationNode);
                operationContainer.outputMessage = parseMessage(doc, operationOutputMessageName);

                operationContainers.add(operationContainer);
            }

            return operationContainers;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return new ArrayList<>();
        }
    }

    private MessageContainer parseMessage(Document doc, String messageName)
    {
        try
        {
            String[] messageNameNamespaceSplit = messageName.split(":");
            if (messageNameNamespaceSplit.length == 2)
                messageName = messageNameNamespaceSplit[1];

            MessageContainer messageContainer = new MessageContainer(messageName);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            // Search for the message element somewhere in the doc
            XPathExpression messageExpr = xpath.compile("//wsdl:message[@name='" + messageName + "']");
            Node messageNode = (Node) messageExpr.evaluate(doc, XPathConstants.NODE);

            XPathExpression messagePartExpr = xpath.compile("wsdl:part");
            NodeList messageParts = (NodeList) messagePartExpr.evaluate(messageNode, XPathConstants.NODESET);

            for (int i = 0; i < messageParts.getLength(); i++)
            {
                Node partNode = messageParts.item(i);
                String partElementName = partNode.getAttributes().getNamedItem("element").getNodeValue();
                messageContainer.parts.add(parseElement(doc, partElementName));
            }

            return messageContainer;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return null;
        }
    }

    private PartContainer parseElement(Document doc, String elementName)
    {
        try
        {
            String[] elementNameNamespaceSplit = elementName.split(":");
            if (elementNameNamespaceSplit.length == 2)
                elementName = elementNameNamespaceSplit[1];

            PartContainer elementContainer = new PartContainer(elementName);

            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(getWsdlNamespaceContext());

            // Search for the element somewhere in the doc
            XPathExpression elementExpr = xpath.compile("//wsdl:types/s:schema/s:element[@name='" + elementName + "']");
            Node elementNode = (Node) elementExpr.evaluate(doc, XPathConstants.NODE);

            // Hierarchy should be elementNode -> complexType -> sequence -> elements that we are looking for
            XPathExpression elementSequenceExpr = xpath.compile("s:complexType/s:sequence/s:element");
            NodeList sequenceElements = (NodeList) elementSequenceExpr.evaluate(elementNode, XPathConstants.NODESET);

            for (int i = 0; i < sequenceElements.getLength(); i++)
            {
                Node sequenceElement = sequenceElements.item(i);
                String sequenceElementName = sequenceElement.getAttributes().getNamedItem("name").getNodeValue();
                String sequenceElementType = sequenceElement.getAttributes().getNamedItem("type").getNodeValue();
                if (shouldMatchElementType(sequenceElementType))
                    elementContainer.subelements.add(sequenceElementName);
            }

            return elementContainer;
        }
        catch (XPathExpressionException ex)
        {
            LOG.error(ex.toString());
            return null;
        }
    }

    private NamespaceContext getWsdlNamespaceContext()
    {
        return new NamespaceContext()
        {
            @Override
            public String getNamespaceURI(String prefix)
            {
                if (prefix.equals("wsdl"))
                    return "http://schemas.xmlsoap.org/wsdl/";
                else if (prefix.equals("s"))
                    return "http://www.w3.org/2001/XMLSchema";
                else
                    return null;
            }

            @Override
            public String getPrefix(String namespaceURI)
            {
                return null;
            }

            @Override
            public Iterator getPrefixes(String namespaceURI)
            {
                return null;
            }
        };
    }

    // Only consider basic elements (those with built-in types such as int, double, string, date, ...) for matching
    private boolean shouldMatchElementType(String type)
    {
        if (type.equals("s:string"))
            return true;
        else if (type.equals("s:double"))
            return true;
        else if (type.equals("s:dateTime"))
            return true;
        else if (type.equals("s:int"))
            return true;
        else
            return false;
    }

    */
    //endregion
}
