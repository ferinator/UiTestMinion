package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GlobalPermissions {
    @XmlElement
    public boolean canUse;
    @XmlElement
    public boolean attachFilesToUserProfile;
    @XmlElement
    public boolean personalSpace;
    @XmlElement
    public boolean createSpace;
    @XmlElement
    public boolean confluenceAdministrator;
    @XmlElement
    public boolean systemAdministrator;
}
