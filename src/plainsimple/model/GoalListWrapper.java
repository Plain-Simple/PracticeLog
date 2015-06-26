package plainsimple.model;

import plainsimple.Goal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/* Helper class to wrap a list of Goals. This is used for saving goalData to XML */
@XmlRootElement(name = "goals")
public class GoalListWrapper {

    private List<Goal> goals;

    @XmlElement(name = "goal")
    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> persons) {
        this.goals = persons;
    }
}