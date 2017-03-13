package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum PageRestrictionType {
    @XmlElement
    NO_RESTRICTIONS,
    @XmlElement
    EDITING_RESTRICTED,
    @XmlElement
    VIEWING_AND_EDITING_RESTRICTED
}
