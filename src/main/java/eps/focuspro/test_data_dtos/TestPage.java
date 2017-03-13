package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TestPage {
    @XmlElement
    public String pageTitle;
    @XmlElement
    public String storageFormat;
    @XmlElement
    public List<String> children; //List of page titles.
    @XmlElement
    public List<String> labels;
    @XmlElement
    public PagePermissions pagePermissions;
    @XmlElement
    public List<TestAttachment> attachments;
    @XmlElement
    public Integer pagePosition;
    @XmlElement
    public boolean isHomePage;

}
