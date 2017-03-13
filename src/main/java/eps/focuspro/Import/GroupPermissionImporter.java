package eps.focuspro.Import;

import com.atlassian.confluence.security.SpacePermission;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import eps.focuspro.Restrictions;

import javax.annotation.Nonnull;

/**
 * Created by cew on 11/4/2016.
 */
public class GroupPermissionImporter extends PermissionImporter {

    public GroupPermissionImporter(SpacePermissionManager spacePermissionManager) {
        super(spacePermissionManager);
    }


    public void setGlobalCanUse(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalCanUsePermission, group);
    }

    public void setGlobalAttachFilesToUserProfile(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalAttachFilesToUserProfilePermission, group);
    }

    public void setGlobalPersonalSpace(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalPersonalSpacePermission, group);
    }

    public void setGlobalCreateSpace(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalCreateSpacePermission, group);
    }

    public void setGlobalConfluenceAdmin(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalConfluenceAdminPermission, group);
    }

    public void setGlobalSystemAdmin(@Nonnull String group) {
        setGlobalGroupPermission(Restrictions.GlobalSystemAdminPermission, group);
    }

    public void setSpaceAll_View(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceAll_ViewPermission, space, group);
    }

    public void setSpacePages_Add(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpacePages_AddPermission, space, group);
    }

    public void setSpacePages_Delete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpacePages_DeletePermission, space, group);
    }

    public void setSpaceBlog_Add(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceBlog_AddPermission, space, group);
    }

    public void setSpaceBlog_Delete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceBlog_DeletePermission, space, group);
    }

    public void setSpaceComments_Add(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceComments_AddPermission, space, group);
    }

    public void setSpaceComments_Delete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceComments_DeletePermission, space, group);
    }

    public void setSpaceAttachments_Add(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceAttachments_AddPermission, space, group);
    }

    public void setSpaceAttachments_Delete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceAttachments_DeletePermission, space, group);
    }

    public void setSpaceRestrictions_AddDelete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceRestrictions_AddDeletePermission, space, group);
    }

    public void setSpaceMail_Delete(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceMail_DeletePermission, space, group);
    }

    public void setSpaceSpace_Export(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceSpace_ExportPermission, space, group);
    }

    public void setSpaceSpace_Admin(@Nonnull String group, @Nonnull Space space) {
        setSpaceGroupPermission(Restrictions.SpaceSpace_AdminPermission, space, group);
    }



    private void setGlobalGroupPermission(String spacePermissionType, String group) {
        SpacePermission spacePermission = SpacePermission.createGroupSpacePermission(spacePermissionType, null, group);
        save(spacePermission);
    }

    private void setSpaceGroupPermission(String spacePermissionType, Space space, String group) {
        SpacePermission spacePermission = SpacePermission.createGroupSpacePermission(spacePermissionType, space, group);
        save(spacePermission);
    }
}
