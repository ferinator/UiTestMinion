package eps.focuspro.export;


import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;
import com.google.common.base.Supplier;
import eps.focuspro.Config;
import eps.focuspro.test_data_dtos.*;

import java.util.ArrayList;
import java.util.List;


public class SpaceExporter {

    private SpaceManager spaceManager;
    private PageManager pageManager;
    private SpacePermissionManager spacePermissionManager;
    private UserAccessor userAccessor;
    private AttachmentManager attachmentManager;
    private String testDataRootPath;

    public SpaceExporter(SpaceManager spaceManager,
                         PageManager pageManager,
                         SpacePermissionManager spacePermissionManager,
                         UserAccessor userAccessor,
                         AttachmentManager attachmentManager,
                         String testDataRootPath) {
        this.spaceManager = spaceManager;
        this.pageManager = pageManager;
        this.spacePermissionManager = spacePermissionManager;
        this.userAccessor = userAccessor;
        this.attachmentManager = attachmentManager;
        this.testDataRootPath = testDataRootPath;
    }

    public void createTestSpaceObject(TestData testData) {
        List<Space> testSpaceList = getTestSpaceList();
        testData.testSpaces = new ArrayList<>();
        for (Space space : testSpaceList) {
            TestSpace testSpace = new TestSpace();
            getSpaceProperties(testSpace, space);
            PageExporter pageExporter = new PageExporter(pageManager, attachmentManager, testDataRootPath);
            testSpace.testPages = pageExporter.createTestPages(space, userAccessor);
            testData.testSpaces.add(testSpace);
        }
    }

    private List<Space> getTestSpaceList() {
        List<Space> listOfAllSpaces = spaceManager.getAllSpaces();
        List<Space> testSpaceList = new ArrayList<Space>();
        for (Space space : listOfAllSpaces) {
            if (space.getDisplayTitle().contains(Config.TestSpaceSignature)) {
                testSpaceList.add(space);
            }
        }
        return testSpaceList;
    }

    private void getSpaceProperties(TestSpace testSpace, Space currentTestSpace) {
        SpacePermissionsExporter spacePermissionsExporter = new SpacePermissionsExporter(spacePermissionManager, userAccessor);
        testSpace.spaceKey = currentTestSpace.getKey();
        testSpace.spaceName = currentTestSpace.getDisplayTitle();
        testSpace.createdBy = currentTestSpace.getCreator().getName();
        testSpace.spacePermissions = spacePermissionsExporter.exportSpacePermissions(currentTestSpace);
    }
}
