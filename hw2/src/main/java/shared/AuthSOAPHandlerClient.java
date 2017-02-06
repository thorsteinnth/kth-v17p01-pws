package shared;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.TreeSet;

public class AuthSOAPHandlerClient implements SOAPHandler<SOAPMessageContext>
{
    public Set<QName> getHeaders()
    {
        return new TreeSet();
    }

    public boolean handleMessage(SOAPMessageContext context)
    {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue())
        {
            // Add authentication header to outgoing messages

            try
            {
                SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();

                SOAPFactory factory = SOAPFactory.newInstance();

                String prefix = "X";
                String uri = "http://pws-hw2-security.se";
                SOAPElement securityElem = factory.createElement("Security", prefix, uri);
                SOAPElement tokenElem = factory.createElement("BinarySecurityToken", prefix, uri);
                tokenElem.addTextNode(SharedData.getAuthToken());
                securityElem.addChildElement(tokenElem);

                SOAPHeader header = envelope.getHeader();
                if (header == null)
                    header = envelope.addHeader();

                header.addChildElement(securityElem);
            }
            catch (Exception e)
            {
                System.out.println("Exception in handler: " + e);
            }
        }
        else
        {
            // Inbound message, do nothing
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