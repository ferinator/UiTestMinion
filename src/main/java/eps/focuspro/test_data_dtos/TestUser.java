package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TestUser {
    @XmlElement
    public String name;
    @XmlElement
    public String fullName;
    @XmlElement
    public String email;
    @XmlElement
    public GlobalPermissions globalPermissions;
    @XmlElement
    public List<String> listedInGroups;
}
