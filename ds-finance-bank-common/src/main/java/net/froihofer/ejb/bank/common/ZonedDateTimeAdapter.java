package net.froihofer.ejb.bank.common;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

// https://www.baeldung.com/jaxb-unmarshalling-dates
// https://www.baeldung.com/java-format-zoned-datetime-string
// https://stackoverflow.com/questions/49458878/java-adding-timezone-to-datetimeformatter
// https://stackoverflow.com/questions/44487065/cant-convert-string-to-zoneddatetime-datetimeparseexception
// WIP
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime unmarshal(String s) {
        return ZonedDateTime.parse(s);
    }

    @Override
    public String marshal(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toString();
    }
}