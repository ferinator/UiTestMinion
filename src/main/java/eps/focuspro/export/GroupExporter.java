package eps.focuspro.export;


import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.Group;
import eps.focuspro.Config;
import eps.focuspro.Restrictions;
import eps.focuspro.test_data_dtos.GlobalPermissions;
import eps.focuspro.test_data_dtos.TestGroup;

import java.util.ArrayList;
import java.util.List;


public class GroupExporter {

    private UserAccessor userAccessor;
    private SpacePermissionManager spacePermissionManager;

    public GroupExporter(UserAccessor userAccessor, SpacePermissionManager spacePermissionManager) {
        this.userAccessor = userAccessor;
        this.spacePermissionManager = spacePermissionManager;
    }

    public List<TestGroup> createTestGroups() {
        List<Group> testGroupList = getTestGroups();
        List<TestGroup> testGroups = new ArrayList<TestGroup>();
        for (Group group : testGroupList) {
            testGroups.add(createTestUserDto(group));
        }
        return testGroups;
    }

    private List<Group> getTestGroups() {
        List<Group> testGroup = userAccessor.getGroupsAsList();
        List<Group> testGroups = new ArrayList<Group>();
        for (Group group : testGroup) {
            if (group.getName().contains(Config.TestGroupSignature)) {
                testGroups.add(group);
            }
        }
        return testGroups;
    }

    private GlobalPermissions createGlobalPermissionsDto(String groupName) {
        GlobalPermissions globalPermissions = new GlobalPermissions();

        globalPermissions.canUse = spacePermissionManager.groupHasPermission(Restrictions.GlobalCanUsePermission, null, groupName);
        globalPermissions.attachFilesToUserProfile = spacePermissionManager.groupHasPermission(Restrictions.GlobalAttachFilesToUserProfilePermission, null, groupName);
        globalPermissions.confluenceAdministrator = spacePermissionManager.groupHasPermission(Restrictions.GlobalConfluenceAdminPermission, null, groupName);
        globalPermissions.createSpace = spacePermissionManager.groupHasPermission(Restrictions.GlobalCreateSpacePermission, null, groupName);
        globalPermissions.personalSpace = spacePermissionManager.groupHasPermission(Restrictions.GlobalPersonalSpacePermission, null, groupName);
        globalPermissions.systemAdministrator = spacePermissionManager.groupHasPermission(Restrictions.GlobalSystemAdminPermission, null, groupName);

        return globalPermissions;
    }

    private TestGroup createTestUserDto(Group currentGroup) {
        TestGroup testGroup = new TestGroup();

        testGroup.name = currentGroup.getName();
        testGroup.globalPermissions = createGlobalPermissionsDto(testGroup.name);

        return testGroup;
    }
}
