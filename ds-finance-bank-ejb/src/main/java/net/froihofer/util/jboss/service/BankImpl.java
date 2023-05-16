package net.froihofer.util.jboss.service;

import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.froihofer.ejb.bank.common.Bank;
import net.froihofer.ejb.bank.common.BankException;
import net.froihofer.ejb.bank.common.PublicStockQuoteDTO;
import net.froihofer.ejb.bank.common.persistence.AddressDTO;
import net.froihofer.ejb.bank.common.persistence.ShareDTO;
import net.froihofer.ejb.bank.common.persistence.UserDTO;
import net.froihofer.util.jboss.entity.PersistenceTranslator;
import net.froihofer.util.jboss.entity.PublicStockQuoteTranslator;
import net.froihofer.util.jboss.persistence.Address;
import net.froihofer.util.jboss.persistence.Share;
import net.froihofer.util.jboss.persistence.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless(name="BankService")
@PermitAll
public class BankImpl implements Bank {

    private static final Logger log = LoggerFactory.getLogger(BankImpl.class);

    @Inject
    PublicStockQuoteTranslator publicStockQuoteTranslator;
    @Inject
    PersistenceTranslator persistenceTranslator;
    private TradingWebService tradingWebService;
    @PersistenceContext(unitName = "ds-finance-bank-persunit")
    private EntityManager entityManager;

    public void setup() {
        log.info("Setup init!!!");
        TradingWebServiceService tradingWebServiceService = new TradingWebServiceService();
        tradingWebService = tradingWebServiceService.getTradingWebServicePort();
        BindingProvider bindingProvider = (BindingProvider) tradingWebService;
        bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "gitUsername");
        bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "gitPw");
    }
    @Override
    public List<PublicStockQuoteDTO> findStockByName(String name) {
        setup();
        log.info("try find Stock by name: " + name);
        try {
            var quotes = tradingWebService.findStockQuotesByCompanyName(name);
            log.info("Stock size: " + quotes.size());
            List<PublicStockQuoteDTO> quoteDTOs = new ArrayList<>();
            for(int i = 0; i < quotes.size(); i++) {
                quoteDTOs.add(publicStockQuoteTranslator.toPublicStockQuoteDTO(quotes.get(i)));
            }
            return quoteDTOs;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public String testMethod(String input) {
        return "test";
    }

    @Override
    public BigDecimal buyStockByName(String symbol, int shares, UserDTO userDTO) throws BankException {
        setup();
        log.info("try buy Stock by symbol: " + symbol + " Amount: " + shares + " with user: " + userDTO);

        try {
            userDTO = findUserByName(userDTO.getFirstName(), userDTO.getLastName());
            if(userDTO == null){
                throw new BankException("Logged in as unknown user!");
            }

            var result = tradingWebService.buy(symbol, shares);
            // Um die Infos über die Firma zu erhalten/PublicStockQuote wird Stockquotes aufgerufen
            var publicStockQuote = findStockBySymbol(symbol);

            createShareAndPersistToUser(userDTO, publicStockQuote.getCompanyName(), shares, result, symbol);

            return result;
        }
        catch (TradingWSException_Exception e) {
            log.error("Bank threw Exception: " + e.getMessage());
            throw new BankException(e.getMessage());
        }
    }

    @Override
    public UserDTO findUserByName(String firstname, String lastname) {
        log.info("Trying to find user by name: " + firstname + " " + lastname);

        List<User> result = entityManager.createQuery(
                        "SELECT u FROM User u "+
                                "WHERE u.firstName LIKE :firstName "+
                                "AND u.lastName LIKE :lastName "+
                                "ORDER BY u.lastName, u.firstName", User.class)
                .setParameter("firstName", "%"+firstname+"%")
                .setParameter("lastName", "%"+lastname+"%")
                .getResultList();

        log.info("Found users: " + result.size());

        if(result.size() > 0) {
            log.info("First user: " + result.get(0));
            return persistenceTranslator.toUserDTO(result.get(0));
        }
        log.error("Bank threw Exception: No User with name " + firstname + " " + lastname + " found!");
        return null;
    }

    @Override
    public UserDTO findUserById(Integer id) {
        log.info("Trying to find user by id: " + id);

        List<User> result = entityManager.createQuery(
                        "SELECT u FROM User u "+
                                "WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList();

        log.info("Found users: " + result.size());

        if(result.size() > 0)
        {
            log.info("First user: " + result.get(0));
            return persistenceTranslator.toUserDTO(result.get(0));
        }
        log.error("Bank threw Exception: No User with id " + id + " found!");
        return null;
    }

    @Override
    public boolean createUser(UserDTO userDTO) {
        log.info("Trying to create user: " + userDTO);

        //Falls es einen User mit denselben Namen gibt, wird dieser nicht nochmal angelegt
        //Kann man natürlich ausbauen mit Geburtstag mehr Daten etc.
        if(findUserByName(userDTO.getFirstName(), userDTO.getLastName()) == null) {
            User user = persistenceTranslator.toUser(userDTO);
            entityManager.persist(user);
            log.info("User creation was successful!");
            return true;
        }
        log.info("User already existed!");
        return false;
    }

    @Override
    public List<UserDTO> queryUsers() {
        List<User> result = entityManager.createQuery(
                        "SELECT u FROM User u", User.class)
                .getResultList();
        if(result.size() > 0)
        {
            log.info("Found Users: " + result.size());
            log.info("First user: " + result.get(0));

            return persistenceTranslator.toUserDTOList(result);
        }
        return null;
    }

    @Override
    public List<AddressDTO> queryAddresses() {
        List<Address> result = entityManager.createQuery(
                        "SELECT a FROM Address a", Address.class)
                .getResultList();
        if(result.size() > 0)
        {
            log.info("Found Addresses: " + result.size());
            log.info("First Address: " + result.get(0));

            return persistenceTranslator.toAddressDTOList(result);
        }
        return null;
    }

    @Override
    public List<ShareDTO> queryShares() {
        List<Share> result = entityManager.createQuery(
                        "SELECT s FROM Share s", Share.class)
                .getResultList();
        if(result.size() > 0)
        {
            log.info("Found Shares: " + result.size());
            log.info("First Share: " + result.get(0));

            return persistenceTranslator.toShareDTOList(result);
        }
        return null;
    }

    @Override
    public PublicStockQuoteDTO findStockBySymbol(String symbol) throws BankException {
        try {
            // Um die Infos über die Firma zu erhalten/PublicStockQuote wird Stockquotes aufgerufen
            var stockQuotes = tradingWebService.getStockQuotes(Arrays.asList(symbol));
            if(stockQuotes.size() < 1)
                throw new BankException("No Share with symbol" + symbol + "found!");

            return publicStockQuoteTranslator.toPublicStockQuoteDTO(stockQuotes.get(0));
        }
        catch (TradingWSException_Exception e) {
            log.error("Bank threw Exception: " + e.getMessage());
            throw new BankException(e.getMessage());
        }
    }
    @Override
    public void createShareAndPersistToUser(UserDTO userDTO, String  companyName, Integer boughtShares, BigDecimal buyPrice, String symbol) {
        Share share = new Share(companyName, boughtShares, buyPrice, System.currentTimeMillis(), symbol);

        var user = persistenceTranslator.toUser(userDTO);
        share.setUser(user);
        entityManager.persist(share);
        user.addShare(share);

        entityManager.merge(user);
        log.info("Creation of share successful. Share: " + share);
    }
}
