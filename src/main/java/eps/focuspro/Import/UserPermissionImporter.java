package eps.focuspro.Import;

import com.atlassian.confluence.security.SpacePermission;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.ConfluenceUser;
import eps.focuspro.Restrictions;

import javax.annotation.Nonnull;

/**
 * Created by cew on 11/4/2016.
 */
public class UserPermissionImporter extends PermissionImporter {

    public UserPermissionImporter(SpacePermissionManager spacePermissionManager) {
        super(spacePermissionManager);
    }


    public void setGlobalCanUse(ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalCanUsePermission, user);
    }

    public void setGlobalAttachFilesToUserProfile(@Nonnull ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalAttachFilesToUserProfilePermission, user);
    }

    public void setGlobalPersonalSpace(@Nonnull ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalPersonalSpacePermission, user);
    }

    public void setGlobalCreateSpace(@Nonnull ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalCreateSpacePermission, user);
    }

    public void setGlobalConfluenceAdmin(@Nonnull ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalConfluenceAdminPermission, user);
    }

    public void setGlobalSystemAdmin(@Nonnull ConfluenceUser user) {
        setGlobalUserPermission(Restrictions.GlobalSystemAdminPermission, user);
    }

    public void setGlobalAnonymousViewUserProfiles() {
        setGlobalUserPermission(Restrictions.GlobalAnonymousViewUserProfilesPermission, (ConfluenceUser) null);
    }

    public void setSpaceAll_View(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceAll_ViewPermission, space, user);
    }

    public void setSpacePages_Add(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpacePages_AddPermission, space, user);
    }

    public void setSpacePages_Delete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpacePages_DeletePermission, space, user);
    }

    public void setSpaceBlog_Add(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceBlog_AddPermission, space, user);
    }

    public void setSpaceBlog_Delete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceBlog_DeletePermission, space, user);
    }

    public void setSpaceComments_Add(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceComments_AddPermission, space, user);
    }

    public void setSpaceComments_Delete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceComments_DeletePermission, space, user);
    }

    public void setSpaceAttachments_Add(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceAttachments_AddPermission, space, user);
    }

    public void setSpaceAttachments_Delete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceAttachments_DeletePermission, space, user);
    }

    public void setSpaceRestrictions_AddDelete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceRestrictions_AddDeletePermission, space, user);
    }

    public void setSpaceMail_Delete(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceMail_DeletePermission, space, user);
    }

    public void setSpaceSpace_Export(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceSpace_ExportPermission, space, user);
    }

    public void setSpaceSpace_Admin(ConfluenceUser user, @Nonnull Space space) {
        setSpaceUserPermission(Restrictions.SpaceSpace_AdminPermission, space, user);
    }



    private void setGlobalUserPermission(String spacePermissionType, ConfluenceUser user) {
        SpacePermission spacePermission;

        if (user != null)
            spacePermission = SpacePermission.createUserSpacePermission(spacePermissionType, null, user);
        else
            spacePermission = SpacePermission.createAnonymousSpacePermission(spacePermissionType, null);

        save(spacePermission);
    }

    private void setSpaceUserPermission(String spacePermissionType, Space space, ConfluenceUser user) {
        SpacePermission spacePermission;

        if (user != null)
            spacePermission = SpacePermission.createUserSpacePermission(spacePermissionType, space, user);
        else
            spacePermission = SpacePermission.createAnonymousSpacePermission(spacePermissionType, space);

        save(spacePermission);
    }
}
