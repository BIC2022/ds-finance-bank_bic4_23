package net.froihofer.util.jboss.service;

import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.froihofer.ejb.bank.common.Bank;
import net.froihofer.ejb.bank.common.BankException;
import net.froihofer.ejb.bank.common.PublicStockQuoteDTO;
import net.froihofer.util.jboss.entity.PublicStockQuoteTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless(name="BankService")
@PermitAll
public class BankImpl implements Bank {

    private static final Logger log = LoggerFactory.getLogger(BankImpl.class);

    @Inject
    PublicStockQuoteTranslator publicStockQuoteTranslator;
    private TradingWebService tradingWebService;
    public void setup() {
        log.info("Setup init!!!");
        TradingWebServiceService tradingWebServiceService = new TradingWebServiceService();
        tradingWebService = tradingWebServiceService.getTradingWebServicePort();
        BindingProvider bindingProvider = (BindingProvider) tradingWebService;
        bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "yourUsername");
        bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "yourPassword");
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
    public BigDecimal buyStockByName(String symbol, int shares) throws BankException {
        setup();
        log.info("try buy Stock by symbol: " + symbol + " Amount: " + shares);

        try {

            return tradingWebService.buy(symbol, shares);

        }
        catch (TradingWSException_Exception e) {
            log.error("Bank threw Exception: " + e.getMessage());
            throw new BankException(e.getMessage());
        }
    }
}
