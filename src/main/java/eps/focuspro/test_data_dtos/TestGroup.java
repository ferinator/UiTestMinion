package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestGroup {
    @XmlElement
    public String name;
    @XmlElement
    public GlobalPermissions globalPermissions;
}
