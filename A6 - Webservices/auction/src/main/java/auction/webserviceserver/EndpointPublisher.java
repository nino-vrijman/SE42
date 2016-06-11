package auction.webserviceserver;

import javax.xml.ws.Endpoint;

/**
 * Created by Nino on 11-6-2016.
 */
public class EndpointPublisher {
    public static String webserviceLocation = "http://localhost:8086/";

    public static void main(String[] args) {
        Endpoint.publish(webserviceLocation + "registration", new RegistrationWS());
        Endpoint.publish(webserviceLocation + "auction", new AuctionWS());
    }
}
