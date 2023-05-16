package net.froihofer.ejb.bank.common;

import net.froihofer.ejb.bank.common.persistence.AddressDTO;
import net.froihofer.ejb.bank.common.persistence.ShareDTO;
import net.froihofer.ejb.bank.common.persistence.UserDTO;

import javax.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface Bank {
    public List<PublicStockQuoteDTO> findStockByName(String name);
    public String testMethod(String input);
    public BigDecimal buyStockByName(String symbol, int shares, UserDTO userDTO) throws BankException;
    public UserDTO findUserByName(String firstname, String lastname) throws BankException;
    public UserDTO findUserById(Integer id) throws BankException;
    public boolean createUser(UserDTO userDTO) throws BankException;
    public List<UserDTO> queryUsers();
    public List<AddressDTO> queryAddresses();
    public List<ShareDTO> queryShares();
    public PublicStockQuoteDTO findStockBySymbol(String symbol) throws BankException;
    public void createShareAndPersistToUser(UserDTO userDTO, String  companyName, Integer boughtShares, BigDecimal buyPrice, String symbol);
}
