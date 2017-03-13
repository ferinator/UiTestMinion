package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Dto for exporting/importing page permissions (called ContentPermissions in Confluence). Only users & groups with the
 * 'test signature' and the admin get exported/imported. Lists can't be null only empty!
 * If all Lists are empty page permissions are set to 'No Restrictions'.
 * If user/group appears 'CanEdit' List but 'CanView' List is empty then page permissions are set to 'Editing Restricted' and user/group has editing rights.
 * If user/group appears 'CanView' List but 'CanEdit' List is empty then page permissions are set to 'View and editing restricted' and user/group has viewing rights.
 * If user/group appears 'CanView' List and 'CanEdit' List then page permissions are set to 'View and editing restricted' and user/group has viewing and editing rights.
 */
@XmlRootElement
public class PagePermissions {
    @XmlElement
    public List<String> usersCanView;
    @XmlElement
    public List<String> usersCanEdit;
    @XmlElement
    public List<String> groupsCanView;
    @XmlElement
    public List<String> groupsCanEdit;
}
