package eps.focuspro.Import;

import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.Group;
import eps.focuspro.Config;
import eps.focuspro.error.UiException;
import eps.focuspro.test_data_dtos.*;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * Created by cew on 11/1/2016.
 */
public class Importer {
    private SpaceManager spaceManager;
    private UserAccessor userAccessor;
    private GroupImporter groupImporter;
    private UserImporter userImporter;
    private SpaceImporter spaceImporter;
    private LabelManager labelManager;
    private AttachmentManager attachmentManager;
    private ContentPermissionManager contentPermissionManager;
    private String testDataRootPath;


    public Importer(String testDataRootPath,
                    SpaceManager spaceManager,
                    PageManager pageManager, UserAccessor userAccessor,
                    SpacePermissionManager spacePermissionManager,
                    LabelManager labelManager,
                    AttachmentManager attachmentManager,
                    ContentPermissionManager contentPermissionManager) {
        this.spaceManager = spaceManager;
        this.userAccessor = userAccessor;
        this.labelManager = labelManager;
        this.attachmentManager = attachmentManager;
        this.contentPermissionManager = contentPermissionManager;
        this.testDataRootPath = testDataRootPath;

        UserPermissionImporter userPermissionImporter = new UserPermissionImporter(spacePermissionManager);
        GroupPermissionImporter groupPermissionImporter = new GroupPermissionImporter(spacePermissionManager);
        userImporter = new UserImporter(userAccessor, userPermissionImporter);
        groupImporter = new GroupImporter(userAccessor, groupPermissionImporter);
        spaceImporter = new SpaceImporter(spaceManager, userPermissionImporter, groupPermissionImporter, pageManager, labelManager, attachmentManager, userAccessor, contentPermissionManager, testDataRootPath);
    }

    public void Import(TestData testData) {
        checkLoggedInAsAdmin();
        removeAllTestSpaces();
        importUsers(testData.testUsers);
        importGroups(testData.testGroups);
        linkUsersToGroups(testData.testUsers);
        importSpaces(testData.testSpaces);
    }

    private void checkLoggedInAsAdmin() {
        String userName = AuthenticatedUserThreadLocal.get().getName();
        if (!userName.equals("admin")) {
            throw new UiException("To import you have to be logged in as 'admin'! You are currently logged in as '" + userName + "'.", new RuntimeException());
        }
    }

    private void removeAllTestSpaces() {
        List<Space> seleniumTestSpaces = spaceManager.getAllSpaces();

        for(Space seleniumTestSpace : seleniumTestSpaces){
            if(seleniumTestSpace.getDisplayTitle().contains(Config.TestSpaceSignature)){
                spaceManager.removeSpace(seleniumTestSpace);
            }
        }
    }

    private void importUsers(List<TestUser> testUsers) {
        for (TestUser testUser : testUsers) {
            ConfluenceUser user = userImporter.createUser(testUser.name, testUser.fullName, testUser.email);
            userImporter.setGlobalUserPermissions(user, testUser.globalPermissions);
        }
    }

    private void importGroups(List<TestGroup> testGroups) {
        for (TestGroup testGroup : testGroups) {
            Group group = groupImporter.createGroup(testGroup.name);
            groupImporter.setGlobalUserPermissions(group.getName(), testGroup.globalPermissions);
        }
    }

    private void linkUsersToGroups(List<TestUser> testUsers) {
        for (TestUser testUser : testUsers) {
            for (String groupName : testUser.listedInGroups) {
                groupImporter.addToGroup(testUser.name, groupName);
            }
        }
    }

    private void importSpaces(List<TestSpace> testSpaces) {
        for (TestSpace testSpace : testSpaces) {
            spaceImporter.createSpaces(testSpace.spaceKey, testSpace.spaceName, userImporter.getConfluenceUser(testSpace.createdBy),testSpace.testPages);
            setSpacePermissions(testSpace.spacePermissions);
        }
    }

    private void setSpacePermissions(SpacePermissions spacePermissions) {
        for (SpacePermissionSet groupPermissionSet : spacePermissions.groups) {
            spaceImporter.setSpacePermissions(groupPermissionSet.name, groupPermissionSet.spaceRestrictions);
        }

        for (SpacePermissionSet userPermissionSet : spacePermissions.users) {
            spaceImporter.setSpacePermissions(userImporter.getConfluenceUser(userPermissionSet.name), userPermissionSet.spaceRestrictions);

        }

        spaceImporter.setSpacePermissionsForAnonymous(spacePermissions.anonymousAccess);
    }
}