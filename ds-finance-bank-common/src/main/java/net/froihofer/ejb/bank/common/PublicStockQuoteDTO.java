package net.froihofer.ejb.bank.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PublicStockQuoteDTO implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(PublicStockQuoteDTO.class);
    @JsonProperty("companyName")
    protected String companyName;
    @JsonProperty("floatShares")
    protected Long floatShares;
    @JsonProperty("lastTradePrice")
    protected BigDecimal lastTradePrice;
    @JsonProperty("lastTradeTime")
    protected ZonedDateTime lastTradeTime;
    @JsonProperty("marketCapitalization")
    protected Long marketCapitalization;
    @JsonProperty("stockExchange")
    protected String stockExchange;
    @JsonProperty("symbol")
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

    @JsonGetter("lastTradeTime")
    public long getLastTradeTime() {
        return lastTradeTime.toInstant().toEpochMilli();
    }

    @JsonSetter("lastTradeTime")
    public void setLastTradeTime(Long lastTradeTime) {
        Instant instant = Instant.ofEpochMilli(lastTradeTime);
        this.lastTradeTime = instant.atZone(ZoneId.of("UTC"));
        log.trace("Set date from timestamp: " + this.lastTradeTime.toString());
    }

    @JsonIgnore
    public ZonedDateTime getLastTradeTimeAsDate() {
        return lastTradeTime;
    }

    @JsonIgnore
    public void setLastTradeTimeFromDate(ZonedDateTime lastTradeTime) {
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
