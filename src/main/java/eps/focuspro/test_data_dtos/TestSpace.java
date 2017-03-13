package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TestSpace {
    @XmlElement
    public String spaceName;
    @XmlElement
    public String spaceKey;
    @XmlElement
    public String createdBy;
    @XmlElement
    public SpacePermissions spacePermissions;
    @XmlElement
    public List<TestPage> testPages;
}





