package shared;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.soap.Node;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class AuthSOAPHandlerServer implements SOAPHandler<SOAPMessageContext>
{
    // https://stackoverflow.com/questions/28487114/retrieving-soap-header-on-jaxws-server-side

    private static final String URI = "http://pws-hw2-security.se";
    private static final QName QNAME_WSSE_BINARYTOKEN = new QName(URI, "BinarySecurityToken");

    public Set<QName> getHeaders()
    {
        return new TreeSet();
    }

    public boolean handleMessage(SOAPMessageContext context)
    {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue())
        {
            // Outgoing message, do nothing
        }
        else
        {
            // Check incoming message for authentication header

            String binaryToken = "";

            try
            {
                SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                Iterator<?> headerElements = header.examineAllHeaderElements();

                while (headerElements.hasNext())
                {
                    SOAPHeaderElement headerElement = (SOAPHeaderElement) headerElements.next();

                    if (headerElement.getElementName().getLocalName().equals("Security"))
                    {
                        Iterator<?> it2 = headerElement.getChildElements();

                        while (it2.hasNext())
                        {
                            Node soapNode = (Node) it2.next();

                            if (soapNode instanceof SOAPElement)
                            {
                                SOAPElement element = (SOAPElement) soapNode;
                                QName elementQname = element.getElementQName();

                                if (QNAME_WSSE_BINARYTOKEN.equals(elementQname))
                                {
                                    SOAPElement binaryTokenElement = element;
                                    binaryToken = binaryTokenElement.getValue();
                                    break;
                                }
                            }
                        }
                    }
                }

                /*
                TODO Now both clients and services are using the same shared hardcoded auth token
                Should be using their own auth token instances, within the services/clients, i.e. not shared
                */
                if (!binaryToken.equals(SharedData.getAuthToken()))
                {
                    // Deny access

                    // We should get the HttpServletResponse and use that to send the error,
                    // but this is not a JavaEE project, so we do it manually like this
                    // 401 Unauthorized
                    context.put("javax.xml.ws.http.response.code", 401);
                    return false;
                }
            }
            catch (Exception e)
            {
                System.err.println("Exception in handler: " + e);
                return false;
            }
        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext context)
    {
        // Do not want to handle faults here, just return true and it will be sent up to the client itself
        return true;
    }

    public void close(MessageContext context)
    {}
}
