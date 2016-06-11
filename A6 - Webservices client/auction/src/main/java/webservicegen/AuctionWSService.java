
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
@WebServiceClient(name = "AuctionWSService", targetNamespace = "http://webserviceserver.auction/", wsdlLocation = "http://localhost:8086/auction?wsdl")
public class AuctionWSService
    extends Service
{

    private final static URL AUCTIONWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUCTIONWSSERVICE_EXCEPTION;
    private final static QName AUCTIONWSSERVICE_QNAME = new QName("http://webserviceserver.auction/", "AuctionWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8086/auction?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUCTIONWSSERVICE_WSDL_LOCATION = url;
        AUCTIONWSSERVICE_EXCEPTION = e;
    }

    public AuctionWSService() {
        super(__getWsdlLocation(), AUCTIONWSSERVICE_QNAME);
    }

    public AuctionWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUCTIONWSSERVICE_QNAME, features);
    }

    public AuctionWSService(URL wsdlLocation) {
        super(wsdlLocation, AUCTIONWSSERVICE_QNAME);
    }

    public AuctionWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUCTIONWSSERVICE_QNAME, features);
    }

    public AuctionWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuctionWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuctionWS
     */
    @WebEndpoint(name = "AuctionWSPort")
    public AuctionWS getAuctionWSPort() {
        return super.getPort(new QName("http://webserviceserver.auction/", "AuctionWSPort"), AuctionWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuctionWS
     */
    @WebEndpoint(name = "AuctionWSPort")
    public AuctionWS getAuctionWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webserviceserver.auction/", "AuctionWSPort"), AuctionWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUCTIONWSSERVICE_EXCEPTION!= null) {
            throw AUCTIONWSSERVICE_EXCEPTION;
        }
        return AUCTIONWSSERVICE_WSDL_LOCATION;
    }

}