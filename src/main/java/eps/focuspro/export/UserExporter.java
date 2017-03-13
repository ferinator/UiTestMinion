package eps.focuspro.export;

import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.User;
import com.atlassian.user.search.page.Pager;
import eps.focuspro.Config;
import eps.focuspro.Restrictions;
import eps.focuspro.error.UiException;
import eps.focuspro.test_data_dtos.GlobalPermissions;
import eps.focuspro.test_data_dtos.TestUser;

import java.util.ArrayList;
import java.util.List;


public class UserExporter {
    private UserAccessor userAccessor;
    private SpacePermissionManager spacePermissionManager;

    public UserExporter(UserAccessor userAccessor, SpacePermissionManager spacePermissionManager) {
        this.userAccessor = userAccessor;
        this.spacePermissionManager = spacePermissionManager;
    }

    public List<TestUser> createTestUsers() {
        List<User> confluenceTestUsers = getTestUsers();
        List<TestUser> testUsers = new ArrayList<TestUser>();
        for (User user : confluenceTestUsers) {
            testUsers.add(createTestUserDto(user));
        }
        return testUsers;
    }

    public List<User> getTestUsers() {
        Pager<User> allUsers = userAccessor.getUsers();
        List<User> testUsers = new ArrayList<User>();
        for (User user : allUsers) {
            if (user.getName().contains(Config.TestUserSignature)) {
                testUsers.add(user);
            }
        }
        return testUsers;
    }

    private GlobalPermissions createUserPermissionsDto(User user){
        GlobalPermissions globalPermissions = new GlobalPermissions();

        globalPermissions.createSpace = spacePermissionManager.hasPermission(Restrictions.GlobalCreateSpacePermission, null, user);
        globalPermissions.personalSpace = spacePermissionManager.hasPermission(Restrictions.GlobalPersonalSpacePermission, null, user);
        globalPermissions.confluenceAdministrator = spacePermissionManager.hasPermission(Restrictions.GlobalConfluenceAdminPermission, null, user);
        globalPermissions.attachFilesToUserProfile = spacePermissionManager.hasPermission(Restrictions.GlobalAttachFilesToUserProfilePermission, null, user);
        globalPermissions.canUse = spacePermissionManager.hasPermission(Restrictions.GlobalCanUsePermission, null, user);
        globalPermissions.systemAdministrator = spacePermissionManager.hasPermission(Restrictions.GlobalSystemAdminPermission, null, user);

        return globalPermissions;
    }

    private TestUser createTestUserDto(User user) {
        TestUser testUser = new TestUser();

        testUser.name = user.getName();
        testUser.fullName = user.getFullName();
        testUser.email = user.getEmail();
        testUser.globalPermissions = createUserPermissionsDto(user);
        testUser.listedInGroups = getGroupNames(user);

        return testUser;
    }

    private List<String> getGroupNames(User user) {
        List<String> groupNames = userAccessor.getGroupNames(user);

        for (String groupName : groupNames) {
            if (!groupName.contains(Config.TestGroupSignature) && !groupName.contains("confluence-users") && !groupName.contains("confluence-administrators"))
                throw new UiException("Test user '" + user.getName() + "' is not allowed to be in the non test group '" + groupName + "'. Users can only be in groups with test group signature so that there is a clean separation between 'normal Confluence' and test logic.", new RuntimeException());
        }
        return groupNames;
    }
}
