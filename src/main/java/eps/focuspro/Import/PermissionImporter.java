package eps.focuspro.Import;

import com.atlassian.confluence.security.SpacePermission;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.ConfluenceUser;
import javax.annotation.*;

/**
 * Use methods of PermissionImporter to set permissions only for permissions that are set in the Confluence configuration.
 * If a permission is not set in the Confluence configuration you don't have to do anything.
 * Use methods with argument 'null' for parameter 'user' to set permissions for anonymous user.
 * Created by cew on 11/4/2016.
 */
public class PermissionImporter {

    private SpacePermissionManager spacePermissionManager;

    public PermissionImporter(SpacePermissionManager spacePermissionManager) {
        this.spacePermissionManager = spacePermissionManager;
    }

    public void removeAllPermissions(ConfluenceUser user) {
        spacePermissionManager.removeAllUserPermissions(user);
    }

    protected void save(SpacePermission spacePermission) {

        //For Confluence versions under 6 we can only use the deprecated savePermission(). The not deprecated was moved
        //to SpacePermissionManagerInternal which we can't inject because we're in an add-on. From Confluence version
        //we can use spacePermissionManager.savePermission(spacePermission, SpacePermissionContext.createDefault());.
        //Because the add-on needs to support Confluence versions under 6 (as well as above 6) we need to use the
        //deprecated method.
        spacePermissionManager.savePermission(spacePermission);
    }
}
