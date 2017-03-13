package eps.focuspro.export;

import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;

import eps.focuspro.FileUtils;
import eps.focuspro.test_data_dtos.TestData;

import eps.focuspro.test_data_dtos.TestUser;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;


public class Exporter {
    private SpaceManager spaceManager;
    private PageManager pageManager;
    private UserAccessor userAccessor;
    private SpacePermissionManager spacePermissionManager;
    private String testDataRootPath;
    private AttachmentManager attachmentManager;

    public Exporter(String testDataRootPath,
                    SpaceManager spaceManager,
                    PageManager pageManager,
                    UserAccessor userAccessor,
                    SpacePermissionManager spacePermissionManager,
                    AttachmentManager attachmentManager) {
        this.spaceManager = spaceManager;
        this.pageManager = pageManager;
        this.userAccessor = userAccessor;
        this.spacePermissionManager = spacePermissionManager;
        this.testDataRootPath = testDataRootPath;
        this.attachmentManager = attachmentManager;
    }

    public void export() {
        AttachmentDownloader attachmentDownloader = new AttachmentDownloader(testDataRootPath);
        attachmentDownloader.cleanAttachmentDirectory();
        createTestDataObject();
    }

    private void createTestDataObject() {
        SpaceExporter spaceExporter = new SpaceExporter(spaceManager, pageManager, spacePermissionManager, userAccessor, attachmentManager, testDataRootPath);
        UserExporter userExporter = new UserExporter(userAccessor, spacePermissionManager);
        GroupExporter groupExporter = new GroupExporter(userAccessor, spacePermissionManager);
        TestData testData = new TestData();

        groupExporter.createTestGroups();
        spaceExporter.createTestSpaceObject(testData);
        testData.testGroups = groupExporter.createTestGroups();
        testData.testUsers = userExporter.createTestUsers();

        createJsonFile(testData);
    }


    private void createJsonFile(TestData testData) {
        try {




            ObjectMapper mapper = new ObjectMapper();


            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(testDataRootPath + FileUtils.TestDataProjectPath + "data.json"), testData);

            // Convert object to JSON string
//            String jsonInString = mapper.writeValueAsString(testUser);
//            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
//            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testUser);
//            System.out.println(jsonInString);


//            String json = gson.toJson(testData);
//            writer.write(json);


//            JAXBContext jaxbContext = JAXBContext.newInstance(TestUser.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            jaxbMarshaller.marshal(testUser, file);
//
//
//
            FileWriter writer = new FileWriter(testDataRootPath + FileUtils.TestDataProjectPath + "data.xml");
            File file = new File(testDataRootPath + FileUtils.TestDataProjectPath + "data.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TestData.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(testData, file);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(testData, sw);

        } catch (Exception wtf) {
            System.out.println(wtf);
        }
    }
}
