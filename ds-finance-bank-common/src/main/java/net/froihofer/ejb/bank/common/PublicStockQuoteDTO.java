package net.froihofer.ejb.bank.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class PublicStockQuoteDTO implements Serializable {
    protected String companyName;
    protected Long floatShares;
    protected BigDecimal lastTradePrice;
    protected ZonedDateTime lastTradeTime;
    protected Long marketCapitalization;
    protected String stockExchange;
    protected String symbol;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getFloatShares() {
        return floatShares;
    }

    public void setFloatShares(Long floatShares) {
        this.floatShares = floatShares;
    }

    public BigDecimal getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(BigDecimal lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public ZonedDateTime getLastTradeTime() {
        return lastTradeTime;
    }

    public void ZonedDateTime(ZonedDateTime lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public PublicStockQuoteDTO(String companyName, Long floatShares, BigDecimal lastTradePrice, ZonedDateTime lastTradeTime, Long marketCapitalization, String stockExchange, String symbol) {
        this.companyName = companyName;
        this.floatShares = floatShares;
        this.lastTradePrice = lastTradePrice;
        this.lastTradeTime = lastTradeTime;
        this.marketCapitalization = marketCapitalization;
        this.stockExchange = stockExchange;
        this.symbol = symbol;
    }
    public PublicStockQuoteDTO() {

    }
}
