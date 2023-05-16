package net.froihofer.ejb.bank.common.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShareDTO implements Serializable {
    private Integer id;
    protected String companyName;
    protected Integer boughtShares;
    protected BigDecimal buyPrice;
    protected Long timeOfPurchase;
    protected String symbol;
    private Integer user;

    public ShareDTO() {

    }

    public ShareDTO(Integer id, String companyName, Integer boughtShares, BigDecimal buyPrice, Long timeOfPurchase, String symbol, Integer user) {
        this.id = id;
        this.companyName = companyName;
        this.boughtShares = boughtShares;
        this.buyPrice = buyPrice;
        this.timeOfPurchase = timeOfPurchase;
        this.symbol = symbol;
        this.user = user;
    }

    public ShareDTO(Integer id, String companyName, Integer boughtShares, BigDecimal buyPrice, Long timeOfPurchase, String symbol) {
        this.id = id;
        this.companyName = companyName;
        this.boughtShares = boughtShares;
        this.buyPrice = buyPrice;
        this.timeOfPurchase = timeOfPurchase;
        this.symbol = symbol;
    }

    public ShareDTO(String companyName, Integer boughtShares, BigDecimal buyPrice, Long timeOfPurchase, String symbol, Integer user) {
        this.companyName = companyName;
        this.boughtShares = boughtShares;
        this.buyPrice = buyPrice;
        this.timeOfPurchase = timeOfPurchase;
        this.symbol = symbol;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getBoughtShares() {
        return boughtShares;
    }

    public void setBoughtShares(Integer boughtShares) {
        this.boughtShares = boughtShares;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public void setTimeOfPurchase(Long timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id);
        builder.append("\nCompany name: " + companyName);
        builder.append("\nBought shares: " + boughtShares);
        builder.append("\nBuy price: " + buyPrice);
        builder.append("\nTime of purchase: " + timeOfPurchase);
        builder.append("\nSymbol: " + symbol);
        builder.append("\nUser ID: " + user);
        return builder.toString();
    }
}
