package eps.focuspro.Import;

import com.atlassian.confluence.user.*;
import com.atlassian.user.User;
import com.atlassian.user.UserManager;
import com.atlassian.user.security.password.Credential;
import eps.focuspro.Config;
import eps.focuspro.test_data_dtos.GlobalPermissions;

/**
 * Created by cew on 11/3/2016.
 */
public class UserImporter {
    private UserAccessor userAccessor;
    private UserPermissionImporter permissionCreator;

    public UserImporter(UserAccessor userAccessor, UserPermissionImporter permissionCreator) {
        this.userAccessor = userAccessor;
        this.permissionCreator = permissionCreator;
    }

    public ConfluenceUser createUser(String userName, String fullName, String email) {
        ConfluenceUser user = userAccessor.getUserByName(userName);

        //Unfortunately to remove all global and space permissions we have to delete the user. The method to remove all
        //permissions in SpacePermissionManager is deprecated and Confluence doesn't offer a public alternative!
        if (user != null)
            userAccessor.removeUser(user);

        User newUser = new ConfluenceUserImpl(userName, fullName, email);
        return userAccessor.createUser(newUser, Credential.unencrypted(Config.GlobalTestUserPassword));
    }

    public ConfluenceUser getConfluenceUser(String user) {
        return userAccessor.getUserByName(user);
    }

    public void setGlobalUserPermissions(ConfluenceUser user, GlobalPermissions globalPermissions) {
        if (globalPermissions.canUse)
            permissionCreator.setGlobalCanUse(user);
        if (globalPermissions.attachFilesToUserProfile)
            permissionCreator.setGlobalAttachFilesToUserProfile(user);
        if (globalPermissions.personalSpace)
            permissionCreator.setGlobalPersonalSpace(user);
        if (globalPermissions.createSpace)
            permissionCreator.setGlobalCreateSpace(user);
        if (globalPermissions.confluenceAdministrator)
            permissionCreator.setGlobalConfluenceAdmin(user);
        if (globalPermissions.systemAdministrator)
            permissionCreator.setGlobalSystemAdmin(user);
    }

    public void removeAllGlobalPermissions() {

    }
}
