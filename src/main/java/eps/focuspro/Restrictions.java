package eps.focuspro;

import com.atlassian.confluence.security.SpacePermission;

/**
 * Created by cew on 07-Nov-16.
 */
public class Restrictions {
    public final static String GlobalCanUsePermission = SpacePermission.USE_CONFLUENCE_PERMISSION;
    public final static String GlobalAttachFilesToUserProfilePermission = SpacePermission.PROFILE_ATTACHMENT_PERMISSION;
    public final static String GlobalPersonalSpacePermission = SpacePermission.PERSONAL_SPACE_PERMISSION;
    public final static String GlobalCreateSpacePermission = SpacePermission.CREATE_SPACE_PERMISSION;
    public final static String GlobalConfluenceAdminPermission = SpacePermission.CONFLUENCE_ADMINISTRATOR_PERMISSION;
    public final static String GlobalSystemAdminPermission = SpacePermission.SYSTEM_ADMINISTRATOR_PERMISSION;
    public final static String GlobalAnonymousViewUserProfilesPermission = SpacePermission.BROWSE_USERS_PERMISSION;
    public final static String SpaceAll_ViewPermission = SpacePermission.VIEWSPACE_PERMISSION;
    public final static String SpacePages_AddPermission = SpacePermission.CREATEEDIT_PAGE_PERMISSION;
    public final static String SpacePages_DeletePermission = SpacePermission.REMOVE_PAGE_PERMISSION;
    public final static String SpaceBlog_AddPermission = SpacePermission.EDITBLOG_PERMISSION;
    public final static String SpaceBlog_DeletePermission = SpacePermission.REMOVE_BLOG_PERMISSION;
    public final static String SpaceComments_AddPermission = SpacePermission.COMMENT_PERMISSION;
    public final static String SpaceComments_DeletePermission = SpacePermission.REMOVE_COMMENT_PERMISSION;
    public final static String SpaceAttachments_AddPermission = SpacePermission.CREATE_ATTACHMENT_PERMISSION;
    public final static String SpaceAttachments_DeletePermission = SpacePermission.REMOVE_ATTACHMENT_PERMISSION;
    public final static String SpaceRestrictions_AddDeletePermission = SpacePermission.SET_PAGE_PERMISSIONS_PERMISSION;
    public final static String SpaceMail_DeletePermission = SpacePermission.REMOVE_MAIL_PERMISSION;
    public final static String SpaceSpace_ExportPermission = SpacePermission.EXPORT_SPACE_PERMISSION;
    public final static String SpaceSpace_AdminPermission = SpacePermission.ADMINISTER_SPACE_PERMISSION;
}
