package ticket.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName = "Ticket",
        portName = "TicketPort",
        targetNamespace = "http://hw2.flightticketreservation/ticket.service/ticket")

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,use=SOAPBinding.Use.LITERAL,parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class TicketService {


}
