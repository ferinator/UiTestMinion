package eps.focuspro.export;

import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.Group;
import com.atlassian.user.User;
import eps.focuspro.Restrictions;
import eps.focuspro.test_data_dtos.SpacePermissionSet;
import eps.focuspro.test_data_dtos.SpacePermissions;
import eps.focuspro.test_data_dtos.SpaceRestrictions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SpacePermissionsExporter {
    private SpacePermissionManager spacePermissionManager;
    private UserAccessor userAccessor;

    public SpacePermissionsExporter(SpacePermissionManager spacePermissionManager, UserAccessor userAccessor) {
        this.spacePermissionManager = spacePermissionManager;
        this.userAccessor = userAccessor;
    }

    public SpacePermissions exportSpacePermissions(Space space) {
        SpacePermissions spacePermissions = new SpacePermissions();
        spacePermissions.users = getUserPermissions(space);
        spacePermissions.groups = getGroupPermissions(space);
        spacePermissions.anonymousAccess = createTestSpaceAnonymousUserRestrictionDtos(space);

        return spacePermissions;
    }

    private List<SpacePermissionSet> getUserPermissions(Space space) {

        Collection<User> usersWithPermissions = spacePermissionManager.getUsersWithPermissions(space);
        List<SpacePermissionSet> spacePermissionUserSet = new ArrayList<SpacePermissionSet>();
        for (User user : usersWithPermissions) {
            spacePermissionUserSet.add(getSpacePermissionSet(space, user));
        }
        return spacePermissionUserSet;
    }

    private SpacePermissionSet getSpacePermissionSet(Space space, User user) {
        SpacePermissionSet spacePermissionSet = new SpacePermissionSet();
        spacePermissionSet.spaceRestrictions = createTestSpaceUserRestrictionsDtos(space, user);
        spacePermissionSet.name = user.getName();


        return spacePermissionSet;
    }

    private List<SpacePermissionSet> getGroupPermissions(Space space) {

        Collection<Group> groupsWIthPermissions = spacePermissionManager.getGroupsWithPermissions(space);
        List<SpacePermissionSet> spacePermissionGroupSet = new ArrayList<SpacePermissionSet>();
        for (Group group : groupsWIthPermissions) {
            spacePermissionGroupSet.add(getSpacePermissionSet(space, group));
        }
        return spacePermissionGroupSet;
    }

    private SpacePermissionSet getSpacePermissionSet(Space space, Group group) {
        SpacePermissionSet spacePermissionSet = new SpacePermissionSet();
        spacePermissionSet.name = group.getName();
        spacePermissionSet.spaceRestrictions = createTestSpaceGroupRestrictionsDtos(space, group.getName());

        return spacePermissionSet;
    }

    private SpaceRestrictions createTestSpaceAnonymousUserRestrictionDtos(Space currentTestSpace) {
        SpaceRestrictions spaceRestrictions = new SpaceRestrictions();

        spaceRestrictions.space_admin = spacePermissionManager.hasPermission(Restrictions.SpaceSpace_AdminPermission, currentTestSpace, null);
        spaceRestrictions.space_export = spacePermissionManager.hasPermission(Restrictions.SpaceSpace_ExportPermission, currentTestSpace, null);
        spaceRestrictions.all_view = spacePermissionManager.hasPermission(Restrictions.SpaceAll_ViewPermission, currentTestSpace, null);
        spaceRestrictions.attachments_add = spacePermissionManager.hasPermission(Restrictions.SpaceAttachments_AddPermission, currentTestSpace, null);
        spaceRestrictions.attachments_delete = spacePermissionManager.hasPermission(Restrictions.SpaceAttachments_DeletePermission, currentTestSpace, null);
        spaceRestrictions.blog_add = spacePermissionManager.hasPermission(Restrictions.SpaceBlog_AddPermission, currentTestSpace, null);
        spaceRestrictions.blog_delete = spacePermissionManager.hasPermission(Restrictions.SpaceBlog_DeletePermission, currentTestSpace, null);
        spaceRestrictions.comments_add = spacePermissionManager.hasPermission(Restrictions.SpaceComments_AddPermission, currentTestSpace, null);
        spaceRestrictions.comments_delete = spacePermissionManager.hasPermission(Restrictions.SpaceComments_DeletePermission, currentTestSpace, null);
        spaceRestrictions.mail_delete = spacePermissionManager.hasPermission(Restrictions.SpaceMail_DeletePermission, currentTestSpace, null);
        spaceRestrictions.pages_add = spacePermissionManager.hasPermission(Restrictions.SpacePages_AddPermission, currentTestSpace, null);
        spaceRestrictions.pages_delete = spacePermissionManager.hasPermission(Restrictions.SpacePages_DeletePermission, currentTestSpace, null);
        spaceRestrictions.restrictions_addDelete = spacePermissionManager.hasPermission(Restrictions.SpaceRestrictions_AddDeletePermission, currentTestSpace, null);

        return spaceRestrictions;
    }

    private SpaceRestrictions createTestSpaceUserRestrictionsDtos(Space currentTestSpace, User user) {
        SpaceRestrictions spaceRestrictions = new SpaceRestrictions();

        spaceRestrictions.space_admin = spacePermissionManager.hasPermission(Restrictions.SpaceSpace_AdminPermission, currentTestSpace, user);
        spaceRestrictions.space_export = spacePermissionManager.hasPermission(Restrictions.SpaceSpace_ExportPermission, currentTestSpace, user);
        spaceRestrictions.all_view = spacePermissionManager.hasPermission(Restrictions.SpaceAll_ViewPermission, currentTestSpace, user);
        spaceRestrictions.attachments_add = spacePermissionManager.hasPermission(Restrictions.SpaceAttachments_AddPermission, currentTestSpace, user);
        spaceRestrictions.attachments_delete = spacePermissionManager.hasPermission(Restrictions.SpaceAttachments_DeletePermission, currentTestSpace, user);
        spaceRestrictions.blog_add = spacePermissionManager.hasPermission(Restrictions.SpaceBlog_AddPermission, currentTestSpace, user);
        spaceRestrictions.blog_delete = spacePermissionManager.hasPermission(Restrictions.SpaceBlog_DeletePermission, currentTestSpace, user);
        spaceRestrictions.comments_add = spacePermissionManager.hasPermission(Restrictions.SpaceComments_AddPermission, currentTestSpace, user);
        spaceRestrictions.comments_delete = spacePermissionManager.hasPermission(Restrictions.SpaceComments_DeletePermission, currentTestSpace, user);
        spaceRestrictions.mail_delete = spacePermissionManager.hasPermission(Restrictions.SpaceMail_DeletePermission, currentTestSpace, user);
        spaceRestrictions.pages_add = spacePermissionManager.hasPermission(Restrictions.SpacePages_AddPermission, currentTestSpace, user);
        spaceRestrictions.pages_delete = spacePermissionManager.hasPermission(Restrictions.SpacePages_DeletePermission, currentTestSpace, user);
        spaceRestrictions.restrictions_addDelete = spacePermissionManager.hasPermission(Restrictions.SpaceRestrictions_AddDeletePermission, currentTestSpace, user);

        return spaceRestrictions;
    }

    private SpaceRestrictions createTestSpaceGroupRestrictionsDtos(Space currentTestSpace, String group) {
        SpaceRestrictions spaceRestrictions = new SpaceRestrictions();

        spaceRestrictions.space_admin = spacePermissionManager.groupHasPermission(Restrictions.SpaceSpace_ExportPermission, currentTestSpace, group);
        spaceRestrictions.space_export = spacePermissionManager.groupHasPermission(Restrictions.SpaceSpace_ExportPermission, currentTestSpace, group);
        spaceRestrictions.all_view = spacePermissionManager.groupHasPermission(Restrictions.SpaceAll_ViewPermission, currentTestSpace, group);
        spaceRestrictions.attachments_add = spacePermissionManager.groupHasPermission(Restrictions.SpaceAttachments_AddPermission, currentTestSpace, group);
        spaceRestrictions.attachments_delete = spacePermissionManager.groupHasPermission(Restrictions.SpaceAttachments_DeletePermission, currentTestSpace, group);
        spaceRestrictions.blog_add = spacePermissionManager.groupHasPermission(Restrictions.SpaceBlog_AddPermission, currentTestSpace, group);
        spaceRestrictions.blog_delete = spacePermissionManager.groupHasPermission(Restrictions.SpaceBlog_DeletePermission, currentTestSpace, group);
        spaceRestrictions.comments_add = spacePermissionManager.groupHasPermission(Restrictions.SpaceComments_AddPermission, currentTestSpace, group);
        spaceRestrictions.comments_delete = spacePermissionManager.groupHasPermission(Restrictions.SpaceComments_DeletePermission, currentTestSpace, group);
        spaceRestrictions.mail_delete = spacePermissionManager.groupHasPermission(Restrictions.SpaceMail_DeletePermission, currentTestSpace, group);
        spaceRestrictions.pages_add = spacePermissionManager.groupHasPermission(Restrictions.SpacePages_AddPermission, currentTestSpace, group);
        spaceRestrictions.pages_delete = spacePermissionManager.groupHasPermission(Restrictions.SpacePages_DeletePermission, currentTestSpace, group);
        spaceRestrictions.restrictions_addDelete = spacePermissionManager.groupHasPermission(Restrictions.SpaceRestrictions_AddDeletePermission, currentTestSpace, group);

        return spaceRestrictions;
    }
}
