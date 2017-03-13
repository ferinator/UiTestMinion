package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class UserGroupSet {
    @XmlElement
    public List<String> users;
    @XmlElement
    public List<String> groups;
}
