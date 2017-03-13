package eps.focuspro.Import;

import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.Group;
import eps.focuspro.test_data_dtos.GlobalPermissions;

/**
 * Created by cew on 11/4/2016.
 */
public class GroupImporter {
    private UserAccessor userAccessor;
    private GroupPermissionImporter permissionCreator;

    public GroupImporter(UserAccessor userAccessor, GroupPermissionImporter permissionCreator) {
        this.userAccessor = userAccessor;
        this.permissionCreator = permissionCreator;
    }

    public Group createGroup(String groupName) {
        Group group = userAccessor.getGroup(groupName);

        //Unfortunately to remove all global and space permissions we have to delete the group. The method to remove all
        //permissions in SpacePermissionManager is deprecated and Confluence doesn't offer a public alternative!
        if (group != null)
            userAccessor.removeGroup(group);

        return userAccessor.createGroup(groupName);
    }

    public Group getGroup(String groupName) {
        return userAccessor.getGroup(groupName);
    }

    public void addToGroup(String userName, String groupName) {
        userAccessor.addMembership(groupName, userName);
    }

    public void setGlobalUserPermissions(String groupName, GlobalPermissions globalPermissions) {
        if (globalPermissions.canUse)
            permissionCreator.setGlobalCanUse(groupName);
        if (globalPermissions.attachFilesToUserProfile)
            permissionCreator.setGlobalAttachFilesToUserProfile(groupName);
        if (globalPermissions.personalSpace)
            permissionCreator.setGlobalPersonalSpace(groupName);
        if (globalPermissions.createSpace)
            permissionCreator.setGlobalCreateSpace(groupName);
        if (globalPermissions.confluenceAdministrator)
            permissionCreator.setGlobalConfluenceAdmin(groupName);
        if (globalPermissions.systemAdministrator)
            permissionCreator.setGlobalSystemAdmin(groupName);
    }
}
