package plainsimple.model;

import plainsimple.Session;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of Sessions. This is used for saving the list
 * of Sessions to XML
 */
public class SessionListWrapper {
    /* Annotates top-most class to be annotated with "sessions" */
    @XmlRootElement(name = "sessions")
    public class PersonListWrapper {

        private List<Session> sessions;

        @XmlElement(name = "session")
        public List<Session> getSessions() {
            return sessions;
        }

        public void setSessions(List<Session> persons) {
            this.sessions = persons;
        }
    }
}
