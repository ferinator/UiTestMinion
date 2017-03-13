package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpaceRestrictions {
    @XmlElement
    public boolean all_view;
    @XmlElement
    public boolean pages_add;
    @XmlElement
    public boolean pages_delete;
    @XmlElement
    public boolean blog_add;
    @XmlElement
    public boolean blog_delete;
    @XmlElement
    public boolean comments_add;
    @XmlElement
    public boolean comments_delete;
    @XmlElement
    public boolean attachments_add;
    @XmlElement
    public boolean attachments_delete;
    @XmlElement
    public boolean restrictions_addDelete;
    @XmlElement
    public boolean mail_delete;
    @XmlElement
    public boolean space_export;
    @XmlElement
    public boolean space_admin;
}
