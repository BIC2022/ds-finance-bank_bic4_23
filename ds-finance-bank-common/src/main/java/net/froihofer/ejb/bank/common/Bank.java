package net.froihofer.ejb.bank.common;

import javax.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface Bank {
    public List<PublicStockQuoteDTO> findStockByName(String name);
    public String testMethod(String input);
    public BigDecimal buyStockByName(String symbol, int shares) throws BankException;
}
