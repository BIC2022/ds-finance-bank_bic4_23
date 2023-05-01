package net.froihofer.ejb.bank.common.restEntities;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "symbol",
        "companyName",
        "stockExchange",
        "floatShares",
        "lastTradePrice",
        "lastTradeTime",
        "marketCapitalization"
})
@Generated("jsonschema2pojo")
public class Stock {
    private static final Logger log = LoggerFactory.getLogger(Stock.class);
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("stockExchange")
    private String stockExchange;
    @JsonProperty("floatShares")
    private Long floatShares;
    @JsonProperty("lastTradePrice")
    private BigDecimal lastTradePrice;

    @JsonProperty("lastTradeTime")
    private ZonedDateTime lastTradeTime;
    @JsonProperty("marketCapitalization")
    private Long marketCapitalization;

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("stockExchange")
    public String getStockExchange() {
        return stockExchange;
    }

    @JsonProperty("stockExchange")
    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    @JsonProperty("floatShares")
    public Long getFloatShares() {
        return floatShares;
    }

    @JsonProperty("floatShares")
    public void setFloatShares(Long floatShares) {
        this.floatShares = floatShares;
    }

    @JsonProperty("lastTradePrice")
    public BigDecimal getLastTradePrice() {
        return lastTradePrice;
    }

    @JsonProperty("lastTradePrice")
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

    @JsonProperty("marketCapitalization")
    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    @JsonProperty("marketCapitalization")
    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }
}