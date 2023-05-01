package net.froihofer.dsfinance.bank.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.BindingProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.froihofer.ejb.bank.common.Bank;
import net.froihofer.ejb.bank.common.BankException_Exception;
import net.froihofer.ejb.bank.common.JaxRsAuthenticator;
import net.froihofer.ejb.bank.common.restEntities.Stock;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import net.froihofer.util.jboss.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
  private static Logger log = LoggerFactory.getLogger(BankClient.class);

  private Client client;
  private WebTarget baseTarget;
  public void setupRest() {
    client = ClientBuilder.newClient()
            .register(new JaxRsAuthenticator("customer1","custom456!"))
            .register(JacksonJsonProvider.class);
    baseTarget = client.target("http://localhost:8080/ds-finance-bank-web/rs/bank");
  }

  /**
   * Skeleton method for performing an RMI lookup
   */
  private Bank getRmiProxy() {
    AuthCallbackHandler.setUsername("customer1");
    AuthCallbackHandler.setPassword("custom456!");
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
      //TODO: Lookup the proxy and assign it to some variable or return it by changing the
      //      return type of this method
      var bank =  jndiHelper.lookup("BankService", Bank.class);
      return bank;
    }
    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
    return null;
  }
  private Bank bank;
  public void getWebService() {

    BankService bankService = new BankService();
    bank = bankService.getBankServicePort();
    BindingProvider bindingProvider = (BindingProvider) bank;
    bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "yourEmployeeUser");
    bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "yourEmployeePW");
  }

  private void run() {
    getWebService();
    setupRest();
    System.out.println("Client Test");

    /* Date Testing
    long stamp = 1682625600000l;
    Timestamp timestamp = new Timestamp(stamp);
    Date test1 = new Date(timestamp.getTime());
    Date test2 = new Date(stamp);

    Instant instant = Instant.ofEpochMilli(stamp);
    var zonedDateTime = instant.atZone(ZoneId.of("UTC"));
    log.info("Set date from timestamp: " + zonedDateTime.toString());
    long timeStamp = zonedDateTime.toInstant().toEpochMilli();
    log.info("Set timestamp from date: " + zonedDateTime.toString());

    String s = "2019-10-04T04:00:00Z[UTC]";
    DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    ZonedDateTime z = ZonedDateTime.parse(s);
    */
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.println("Search|buy|sell|history");
      var option = scanner.nextLine().toLowerCase();

      switch (option) {
        case "search": {
          System.out.println("Enter name to search: ");
          var name = scanner.nextLine();
          var test = bank.findStockByName(name);
          System.out.println("Search result");
          for(int i = 0; i < test.size(); i++) {
            System.out.printf("%s: Symbol = %s Available shares: %d\n", test.get(i).getCompanyName(), test.get(i).getSymbol(), test.get(i).getFloatShares());
          }
          break;
        }

        case "buy": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          System.out.println("Enter amount: ");
          var amount = scanner.nextLine();
          try {
            int shares = Integer.parseInt(amount);
            var result = bank.buyStockByName(symbol, shares);
            System.out.println("Cost per share: " + result);
            System.out.println("Overall cost: " + result.multiply(BigDecimal.valueOf(shares)));

          } catch (BankException_Exception e) {
            log.error("Bank threw Exception: "+ e.getMessage());
          }  catch (Exception e) {
            log.error("Something did not work, see stack trace.", e);
            e.printStackTrace();
          }
          break;
        }

        case "sell": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          System.out.println("Enter amount: ");
          var amount = scanner.nextLine();
          try {

            int shares = Integer.parseInt(amount);

            WebTarget getTarget = baseTarget.path("sell")
                    .queryParam("symbol", symbol)
                    .queryParam("amount", shares);

            var response = getTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(response.getStatus() != Response.Status.OK.getStatusCode()) {
              throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
            }

            var res = response.readEntity(BigDecimal.class);

            System.out.println("Cost per share: " + res);
            System.out.println("Overall cost: " + res.multiply(BigDecimal.valueOf(shares)));
          }  catch (Exception e) {
            log.error("Something did not work, see stack trace.", e);
            e.printStackTrace();
          }
          break;
        }

        case "history": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          try {


            WebTarget getTarget = baseTarget.path("history")
                    .queryParam("symbol", symbol);

            var response = getTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(response.getStatus() != Response.Status.OK.getStatusCode()) {
              throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
            }
            var res = response.readEntity(new GenericType<ArrayList<Stock>>(){});

            System.out.println("History result");
            for(int i = 0; i < res.size(); i++) {
              System.out.printf("companyName: %s: Symbol: %s Available shares: %d lastTradePrice: %s lastTradeTime: %s\n",
                      res.get(i).getCompanyName(), res.get(i).getSymbol(), res.get(i).getFloatShares(),
                      res.get(i).getLastTradePrice().toString(), res.get(i).getLastTradeTimeAsDate());
            }
          }  catch (Exception e) {
            log.error("Something did not work, see stack trace.", e);
            e.printStackTrace();
          }
          break;
        }
      }
    }
  }

  // Info zu Datetime Klassen
  // https://i.stack.imgur.com/1cse2.png
  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.run();
  }
}
