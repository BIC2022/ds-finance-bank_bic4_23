package net.froihofer.dsfinance.bank.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.froihofer.ejb.bank.common.Bank;
import net.froihofer.ejb.bank.common.BankException;
import net.froihofer.ejb.bank.common.JaxRsAuthenticator;
import net.froihofer.ejb.bank.common.PublicStockQuoteDTO;
import net.froihofer.ejb.bank.common.persistence.AddressDTO;
import net.froihofer.ejb.bank.common.persistence.ShareDTO;
import net.froihofer.ejb.bank.common.persistence.UserDTO;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
  private static Logger log = LoggerFactory.getLogger(BankClient.class);

  private Client client;
  private Bank bank;
  private WebTarget baseTarget;
  private UserDTO userDTO;
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

  private void run() {
    bank = getRmiProxy();
    setupRest();
    System.out.println("Client Test");
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.println("Search|buy|sell|history|create|login|query");
      var option = scanner.nextLine().toLowerCase();

      switch (option) {
        case "search": {
          System.out.println("Enter name to search: ");
          var name = scanner.nextLine();
          search(name);
          break;
        }

        case "buy": {
          if(userDTO == null) {
            System.out.println("Not logged in with a user!");
            break;
          }


          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          System.out.println("Enter amount: ");
          var amount = scanner.nextLine();
          buy(symbol, amount);
          break;
        }

        case "sell": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          System.out.println("Enter amount: ");
          var amount = scanner.nextLine();
          sell(symbol, amount);
          break;
        }

        case "selltwo": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          System.out.println("Enter amount: ");
          var amount = scanner.nextLine();
          selltwo(symbol, amount);
          break;
        }

        case "history": {
          System.out.println("Enter symbol: ");
          var symbol = scanner.nextLine();
          history(symbol);
          break;
        }

        case "login": {
          System.out.println("Login via name|id?");
          var login = scanner.nextLine().toLowerCase();
          if(login.equals("name")) {
            System.out.println("Enter the firstname: ");
            var firstname = scanner.nextLine();
            System.out.println("Enter the lastname: ");
            var lastname = scanner.nextLine();
            loginName(firstname, lastname);
          } else if(login.equals("id")) {
            System.out.println("Enter the ID: ");
            var id = scanner.nextLine();
            loginID(id);
          } else
            System.out.println("Invalid input.");
          break;
        }

        case "create": {
          System.out.println("Enter the firstname: ");
          var firstname = scanner.nextLine();
          System.out.println("Enter the lastname: ");
          var lastname = scanner.nextLine();
          System.out.println("Enter the adress(space separated): ");
          var address = scanner.nextLine();
          create(firstname, lastname, address);
          break;
        }

        case "query": {
          System.out.println("users|addresses|shares");
          var queries = scanner.nextLine().toLowerCase();

          switch (queries) {
            case "users": {
              queryUsers();
              break;
            }
            case "addresses": {
              queryAddresses();
              break;
            }
            case "shares": {
              queryShares();
              break;
            }
          }
          break;
        }
      }
    }
  }

  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.run();
  }

  public void buy(String symbol, String amount) {
    try {
      int shares = Integer.parseInt(amount);
      var result = bank.buyStockByName(symbol, shares, userDTO);
      System.out.println("Cost per share: " + result);
      System.out.println("Overall cost: " + result.multiply(BigDecimal.valueOf(shares)));

    } catch (BankException e) {
      log.error("Bank threw Exception: "+ e.getMessage());
    }  catch (Exception e) {
      log.error("Something did not work, see stack trace.", e);
      e.printStackTrace();
    }
  }
  public void sell(String symbol, String amount) {
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
  }

  public void selltwo(String symbol, String amount) {
    try {

      int shares = Integer.parseInt(amount);

      WebTarget postTarget = baseTarget.path("selltwo")
              .queryParam("symbol", symbol)
              .queryParam("amount", shares);

      var response = postTarget.request(MediaType.APPLICATION_JSON_TYPE)
              .post(Entity.json((userDTO.getFirstName() + " " + userDTO.getLastName())));

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
  }
  public void search(String name) {
    var test = bank.findStockByName(name);
    System.out.println("Search result");
    for(int i = 0; i < test.size(); i++) {
      System.out.printf("%s: Symbol = %s Available shares: %d\n", test.get(i).getCompanyName(), test.get(i).getSymbol(), test.get(i).getFloatShares());
    }
  }
  public void history(String symbol) {
    try {
      WebTarget getTarget = baseTarget.path("history")
              .queryParam("symbol", symbol);

      var response = getTarget.request(MediaType.APPLICATION_JSON_TYPE)
              .get();

      if(response.getStatus() != Response.Status.OK.getStatusCode()) {
        throw new WebApplicationException(response.getStatusInfo().getReasonPhrase());
      }
      var res = response.readEntity(new GenericType<ArrayList<PublicStockQuoteDTO>>(){});

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
  }

  public void loginID(String id) {
    try {
      userDTO = bank.findUserById(Integer.parseInt(id));
      System.out.println("Login successful.");
      System.out.println(userDTO);
    } catch (BankException e) {
      log.error("Something did not work, see Message: ", e.getMessage());
    } catch (Exception e) {
      log.error("Something did not work, see stack trace.", e);
      e.printStackTrace();
    }
  }
  public void loginName(String firstname, String lastname) {
    try {
      userDTO = bank.findUserByName(firstname, lastname);
      System.out.println("Login successful.");
      System.out.println(userDTO);
    } catch (BankException e) {
      log.error("Something did not work, see Message: ", e.getMessage());
    } catch (Exception e) {
      log.error("Something did not work, see stack trace.", e);
      e.printStackTrace();
    }
  }
  public void create(String firstname, String lastname, String address) {
    try {
      AddressDTO test_Adress = new AddressDTO();
      UserDTO test_User = new UserDTO(firstname, lastname);
      var split = address.split(" ");
      if(split.length < 4)
        throw new Exception("No enough input parameters");

      test_Adress.setStreet(split[0]);
      test_Adress.setHouseNo(split[1]);
      test_Adress.setPlace(split[2]);
      test_Adress.setZipCode(Integer.parseInt(split[3]));
      test_User.addAddress(test_Adress);
      test_Adress.setUser(test_User.getId());

      boolean res = bank.createUser(test_User);
      System.out.println("User creation: " + (res ? "successful." : "failed!"));
      if(res) {
        System.out.println("You are automatically logged in as the new user");
        loginName(firstname, lastname);
      } else {
        System.out.println("Maybe the user already exists?");
      }
    } catch (BankException e) {
      log.error("Something did not work, see Message: ", e.getMessage());
    } catch (Exception e) {
      log.error("Something did not work, see stack trace.", e);
      e.printStackTrace();
    }
  }

  public void queryUsers() {
    List<UserDTO> userDTOList = bank.queryUsers();
    for(int i = 0; i < userDTOList.size(); i++) {
      System.out.println(userDTOList.get(i));
    }
  }
  public void queryAddresses() {
    List<AddressDTO> addressDTOList = bank.queryAddresses();
    for(int i = 0; i < addressDTOList.size(); i++) {
      System.out.println(addressDTOList.get(i));
    }
  }

  public void queryShares() {
    List<ShareDTO> shareDTOList = bank.queryShares();
    for(int i = 0; i < shareDTOList.size(); i++) {
      System.out.println(shareDTOList.get(i));
    }
  }
}
