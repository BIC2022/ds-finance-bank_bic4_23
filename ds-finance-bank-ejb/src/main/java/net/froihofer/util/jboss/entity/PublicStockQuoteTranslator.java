package net.froihofer.util.jboss.entity;

import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.ejb.bank.common.PublicStockQuoteDTO;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class PublicStockQuoteTranslator {
    public PublicStockQuoteDTO toPublicStockQuoteDTO (PublicStockQuote publicStockQuote) {
        if(publicStockQuote == null)
            return null;
        return new PublicStockQuoteDTO(publicStockQuote.getCompanyName(),
                publicStockQuote.getFloatShares(),
                publicStockQuote.getLastTradePrice(),
                publicStockQuote.getLastTradeTime() != null ? publicStockQuote.getLastTradeTime().toGregorianCalendar().toZonedDateTime(): null,
                publicStockQuote.getMarketCapitalization(),
                publicStockQuote.getStockExchange(),
                publicStockQuote.getSymbol());
    }

    public PublicStockQuote toPublicStockQuote (PublicStockQuoteDTO publicStockQuote) {
        if(publicStockQuote == null)
            return null;
        PublicStockQuote pq = new PublicStockQuote();
        pq.setCompanyName(publicStockQuote.getCompanyName());
        pq.setFloatShares(publicStockQuote.getFloatShares());
        pq.setLastTradePrice(publicStockQuote.getLastTradePrice());

        try {
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(publicStockQuote.getLastTradeTimeAsDate());
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            pq.setLastTradeTime(xmlGregorianCalendar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pq.setMarketCapitalization(publicStockQuote.getMarketCapitalization());
        pq.setStockExchange(publicStockQuote.getStockExchange());
        pq.setSymbol(publicStockQuote.getSymbol());
        return pq;
    }
}
