package plainsimple.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

/* Adapter (for JAXB) to convert between the LocalTime and the
 * String representation of the time such as '12:05:00'. */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v);
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}