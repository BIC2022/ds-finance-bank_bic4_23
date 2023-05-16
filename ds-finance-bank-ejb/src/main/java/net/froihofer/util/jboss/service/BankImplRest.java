package net.froihofer.util.jboss.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.froihofer.ejb.bank.common.Bank;
import net.froihofer.ejb.bank.common.BankException;
import net.froihofer.ejb.bank.common.JaxRsAuthenticator;
import net.froihofer.ejb.bank.common.PublicStockQuoteDTO;
import net.froihofer.ejb.bank.common.persistence.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Path("/bank")
@Produces("application/json")
@Consumes("application/json")
public class BankImplRest {

    private static final Logger log = LoggerFactory.getLogger(BankImplRest.class);

    //@EJB(lookup = "java:ds-finance-bank-ear-1.0-SNAPSHOT.ear/net.froihofer-ds-finance-bank-ejb-1.0-SNAPSHOT.jar/BankService")
    @EJB(lookup = "ejb:ds-finance-bank-ear/ds-finance-bank-ejb//BankService!net.froihofer.ejb.bank.common.Bank")
    private Bank bank;
    private Client client;
    private WebTarget baseTarget;


    // Testmethod
    @GET
    @Path("/greeting")
    @Produces("text/plain")
    public String greet() {
        return "hello";
    }

    public void setup() {
        client = ClientBuilder.newClient()
                .register(new JaxRsAuthenticator("gitUsername","gitPw"))
                .register(JacksonJsonProvider.class);
        baseTarget = client.target("https://edu.dedisys.org/ds-finance/ws/rs/trading/stock");
    }

    //http://localhost:8080/ds-finance-bank-web/rs/bank/history?symbol=AABA

    @GET
    @Path("/history")
    public Response history(@QueryParam("symbol") String symbol) throws UnsupportedEncodingException {
        setup();
        log.info("Rest input value: " + symbol);
        WebTarget getTarget = baseTarget.path("{symbol}").path("history");
        var result = getTarget.resolveTemplate("symbol", symbol).request()
                .get(new GenericType<ArrayList<PublicStockQuoteDTO>>(){});
        log.info("Rest output count: " + result.size());
        GenericEntity<ArrayList<PublicStockQuoteDTO>> list = new GenericEntity<ArrayList<PublicStockQuoteDTO>>(result) {};
        return Response.ok(list).build();
    }

    public void search(String name) {
        WebTarget getTarget = baseTarget.path("history").queryParam("partOfCompanyName", name);
        System.out.println(getTarget.request().get().readEntity(String.class));

    }

    public void quote(String symbol) {
        WebTarget getTarget = baseTarget.path("{symbol}").path("quote");
        var response = getTarget.resolveTemplate("symbol", symbol);
        System.out.println(getTarget.request().get().readEntity(String.class));

    }

    //http://localhost:8080/ds-finance-bank-web/rs/bank/buy?symbol=AABA&amount=1

    @GET
    @Path("/buy")
    public Response buy(@QueryParam("symbol") String symbol, @QueryParam("amount") int amount) {
        setup();
        try {
            log.info("Rest input symbol: " + symbol + " amount: " + amount);

            WebTarget postTarget = baseTarget.path("{symbol}").path("buy");
            var response = postTarget.resolveTemplate("symbol", symbol)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(amount), BigDecimal.class);

            log.info("Rest output: " + response);
            return Response.ok(response).build();
        }catch (Exception e) {
            System.out.println("Something happened");
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request." + e.getMessage()).build();
        }
    }

    //http://localhost:8080/ds-finance-bank-web/rs/bank/sell?symbol=AABA&amount=1


    @GET
    @Path("/sell")
    public Response sell(@QueryParam("symbol") String symbol, @QueryParam("amount") int amount) {
        setup();
        try {
            log.info("Rest input symbol: " + symbol + " amount: " + amount);

            WebTarget postTarget = baseTarget.path("{symbol}").path("sell");
            var response = postTarget.resolveTemplate("symbol", symbol)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(amount), BigDecimal.class);

            log.info("Rest output: " + response);
            return Response.ok(response).build();
        }catch (Exception e) {
            System.out.println("Something happened");
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request." + e.getMessage()).build();
        }
    }

    @POST
    @Path("selltwo")
    public Response sell2(String name, @QueryParam("symbol") String symbol, @QueryParam("amount") int amount){
        setup();
        try {
            String[] split = name.split(" ");
            log.info("Rest input symbol: " + symbol + " amount: " + amount);
            log.info("Selling from user: " + split[0] + " " + split[1]);

            UserDTO userDTO = bank.findUserByName(split[0], split[1]);

            if(userDTO == null){
                throw new BankException("Logged in as unknown user!");
            }

            WebTarget postTarget = baseTarget.path("{symbol}").path("sell");
            var response = postTarget.resolveTemplate("symbol", symbol)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(amount), BigDecimal.class);

            log.info("Rest output: " + response);

            // Um die Infos über die Firma zu erhalten/PublicStockQuote wird Stockquotes aufgerufen
            var publicStockQuote = bank.findStockBySymbol(symbol);

            bank.createShareAndPersistToUser(userDTO, publicStockQuote.getCompanyName(), amount, response, symbol);

            return Response.ok(response).build();
        }catch (Exception e) {
            System.out.println("Something happened");
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request." + e.getMessage()).build();
        }
    }
}

