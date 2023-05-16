package net.froihofer.util.jboss.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="SHARES")
public class Share implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="COMPANY_NAME")
    protected String companyName;
    @Column(name="BOUGHT_SHARES")
    protected Integer boughtShares;
    @Column(name="BUY_PRICE")
    protected BigDecimal buyPrice;
    @Column(name="TIME_OF_PURCHASE")
    protected Long timeOfPurchase;
    @Column(name="SYMBOL")
    protected String symbol;
    @ManyToOne
    @JoinColumn(name="USER_FK")
    private User user;

    public Share() {

    }

    public Share(Integer id, String companyName, Integer boughtShares, BigDecimal buyPrice, Long timeOfPurchase, String symbol) {
        this.id = id;
        this.companyName = companyName;
        this.boughtShares = boughtShares;
        this.buyPrice = buyPrice;
        this.timeOfPurchase = timeOfPurchase;
        this.symbol = symbol;
    }

    public Share(String companyName, Integer boughtShares, BigDecimal buyPrice, Long timeOfPurchase, String symbol) {
        this.companyName = companyName;
        this.boughtShares = boughtShares;
        this.buyPrice = buyPrice;
        this.timeOfPurchase = timeOfPurchase;
        this.symbol = symbol;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        return builder.toString();
    }
}
