
package webservicegen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "RegistrationWSService", targetNamespace = "http://webserviceserver.auction/", wsdlLocation = "http://localhost:8086/registration?wsdl")
public class RegistrationWSService
    extends Service
{

    private final static URL REGISTRATIONWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException REGISTRATIONWSSERVICE_EXCEPTION;
    private final static QName REGISTRATIONWSSERVICE_QNAME = new QName("http://webserviceserver.auction/", "RegistrationWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8086/registration?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        REGISTRATIONWSSERVICE_WSDL_LOCATION = url;
        REGISTRATIONWSSERVICE_EXCEPTION = e;
    }

    public RegistrationWSService() {
        super(__getWsdlLocation(), REGISTRATIONWSSERVICE_QNAME);
    }

    public RegistrationWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), REGISTRATIONWSSERVICE_QNAME, features);
    }

    public RegistrationWSService(URL wsdlLocation) {
        super(wsdlLocation, REGISTRATIONWSSERVICE_QNAME);
    }

    public RegistrationWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, REGISTRATIONWSSERVICE_QNAME, features);
    }

    public RegistrationWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RegistrationWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns RegistrationWS
     */
    @WebEndpoint(name = "RegistrationWSPort")
    public RegistrationWS getRegistrationWSPort() {
        return super.getPort(new QName("http://webserviceserver.auction/", "RegistrationWSPort"), RegistrationWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RegistrationWS
     */
    @WebEndpoint(name = "RegistrationWSPort")
    public RegistrationWS getRegistrationWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webserviceserver.auction/", "RegistrationWSPort"), RegistrationWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (REGISTRATIONWSSERVICE_EXCEPTION!= null) {
            throw REGISTRATIONWSSERVICE_EXCEPTION;
        }
        return REGISTRATIONWSSERVICE_WSDL_LOCATION;
    }

}
