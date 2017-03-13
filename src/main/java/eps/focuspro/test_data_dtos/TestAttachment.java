package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TestAttachment {
    @XmlElement
    public String name;
    @XmlElement
    public long id;
    @XmlElement
    public String filepath;
    @XmlElement
    public String fileName;
    @XmlElement
    public String comment;
    @XmlElement
    public String creator;
    @XmlElement
    public List<String> attachmentLabels;
    @XmlElement
    public String mediaType;
    @XmlElement
    public Long fileSize;
    @XmlElement
    public int version;
}
