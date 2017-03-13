package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class SpacePermissions {
    @XmlElement
    public List<SpacePermissionSet> groups;
    @XmlElement
    public List<SpacePermissionSet> users;
    @XmlElement
    public SpaceRestrictions anonymousAccess;
}
