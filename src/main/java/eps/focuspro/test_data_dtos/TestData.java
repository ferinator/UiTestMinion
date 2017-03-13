package eps.focuspro.test_data_dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TestData {
    @XmlElement
    public List<TestGroup> testGroups;
    @XmlElement
    public List<TestUser> testUsers;
    @XmlElement
    public List<TestSpace> testSpaces;
}


