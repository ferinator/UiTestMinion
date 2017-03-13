package eps.focuspro.Import;

import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.user.User;
import eps.focuspro.test_data_dtos.SpaceRestrictions;
import eps.focuspro.test_data_dtos.TestPage;

import java.util.List;

/**
 * Created by cew on 10/31/2016.
 */
public class SpaceImporter {
    private Space newSpace;
    private SpaceManager spaceManager;
    private UserPermissionImporter userPermissionImporter;
    private GroupPermissionImporter groupPermissionImporter;
    private PageManager pageManager;
    private LabelManager labelManager;
    private PageImporter pageImporter;
    private AttachmentManager attachmentManager;
    private UserAccessor userAccessor;
    private ContentPermissionManager contentPermissionManager;
    private String testDataRootPath;


    public SpaceImporter(SpaceManager spaceManager,
                         UserPermissionImporter userPermissionImporter,
                         GroupPermissionImporter groupPermissionImporter,
                         PageManager pageManager, LabelManager labelManager,
                         AttachmentManager attachmentManager,
                         UserAccessor userAccessor, ContentPermissionManager contentPermissionManager,
                         String testDataRootPath
                         ) {
        this.spaceManager = spaceManager;
        this.userPermissionImporter = userPermissionImporter;
        this.groupPermissionImporter = groupPermissionImporter;
        this.pageManager = pageManager;
        this.labelManager = labelManager;
        this.attachmentManager = attachmentManager;
        this.userAccessor = userAccessor;
        this.contentPermissionManager = contentPermissionManager;
        this.testDataRootPath = testDataRootPath;
    }

    public void createSpaces(String spaceKey, String spaceName, User creator, List<TestPage> testPages) {
        System.out.println("----------------------------------------\n");
        System.out.println("spacekey: " + spaceKey);
        System.out.println("spacename: " + spaceName);
        System.out.println("creator: " + creator);
        newSpace = spaceManager.createSpace(spaceKey, spaceName, "Ui Test Space", creator);

        spaceManager.saveSpace(newSpace);
        removeHomepage();

        pageImporter = new PageImporter(spaceManager, pageManager, newSpace, labelManager, attachmentManager, userAccessor, contentPermissionManager, testDataRootPath);
        Page rootPage = pageImporter.createPages(testPages);
        newSpace.setHomePage(rootPage);
    }

    private void removeHomepage(){
//        pageManager.removeAllPages(newSpace);
        newSpace.getHomePage().remove(pageManager);
    }

    public void setSpacePermissions(String groupName, SpaceRestrictions restrictions) {
        checkCurrentSpaceForNull();

        if (restrictions.all_view)
            groupPermissionImporter.setSpaceAll_View(groupName, newSpace);
        if (restrictions.pages_add)
            groupPermissionImporter.setSpacePages_Add(groupName, newSpace);
        if (restrictions.pages_delete)
            groupPermissionImporter.setSpacePages_Delete(groupName, newSpace);
        if (restrictions.blog_add)
            groupPermissionImporter.setSpaceBlog_Add(groupName, newSpace);
        if (restrictions.blog_delete)
            groupPermissionImporter.setSpaceBlog_Delete(groupName, newSpace);
        if (restrictions.comments_add)
            groupPermissionImporter.setSpaceComments_Add(groupName, newSpace);
        if (restrictions.comments_delete)
            groupPermissionImporter.setSpaceComments_Delete(groupName, newSpace);
        if (restrictions.attachments_add)
            groupPermissionImporter.setSpaceAttachments_Add(groupName, newSpace);
        if (restrictions.attachments_delete)
            groupPermissionImporter.setSpaceAttachments_Delete(groupName, newSpace);
        if (restrictions.restrictions_addDelete)
            groupPermissionImporter.setSpaceRestrictions_AddDelete(groupName, newSpace);
        if (restrictions.mail_delete)
            groupPermissionImporter.setSpaceMail_Delete(groupName, newSpace);
        if (restrictions.space_export)
            groupPermissionImporter.setSpaceSpace_Export(groupName, newSpace);
        if (restrictions.space_admin)
            groupPermissionImporter.setSpaceSpace_Admin(groupName, newSpace);
    }

    public void setSpacePermissions(ConfluenceUser user, SpaceRestrictions restrictions) {
        checkCurrentSpaceForNull();

        if (restrictions.all_view)
            userPermissionImporter.setSpaceAll_View(user, newSpace);
        if (restrictions.pages_add)
            userPermissionImporter.setSpacePages_Add(user, newSpace);
        if (restrictions.pages_delete)
            userPermissionImporter.setSpacePages_Delete(user, newSpace);
        if (restrictions.blog_add)
            userPermissionImporter.setSpaceBlog_Add(user, newSpace);
        if (restrictions.blog_delete)
            userPermissionImporter.setSpaceBlog_Delete(user, newSpace);
        if (restrictions.comments_add)
            userPermissionImporter.setSpaceComments_Add(user, newSpace);
        if (restrictions.comments_delete)
            userPermissionImporter.setSpaceComments_Delete(user, newSpace);
        if (restrictions.attachments_add)
            userPermissionImporter.setSpaceAttachments_Add(user, newSpace);
        if (restrictions.attachments_delete)
            userPermissionImporter.setSpaceAttachments_Delete(user, newSpace);
        if (restrictions.restrictions_addDelete)
            userPermissionImporter.setSpaceRestrictions_AddDelete(user, newSpace);
        if (restrictions.mail_delete)
            userPermissionImporter.setSpaceMail_Delete(user, newSpace);
        if (restrictions.space_export)
            userPermissionImporter.setSpaceSpace_Export(user, newSpace);
        if (restrictions.space_admin)
            userPermissionImporter.setSpaceSpace_Admin(user, newSpace);
    }

    public void setSpacePermissionsForAnonymous(SpaceRestrictions restrictions) {
        checkCurrentSpaceForNull();

        if (restrictions.all_view)
            userPermissionImporter.setSpaceAll_View(null, newSpace);
        if (restrictions.pages_add)
            userPermissionImporter.setSpacePages_Add(null, newSpace);
        if (restrictions.pages_delete)
            userPermissionImporter.setSpacePages_Delete(null, newSpace);
        if (restrictions.blog_add)
            userPermissionImporter.setSpaceBlog_Add(null, newSpace);
        if (restrictions.blog_delete)
            userPermissionImporter.setSpaceBlog_Delete(null, newSpace);
        if (restrictions.comments_add)
            userPermissionImporter.setSpaceComments_Add(null, newSpace);
        if (restrictions.comments_delete)
            userPermissionImporter.setSpaceComments_Delete(null, newSpace);
        if (restrictions.attachments_add)
            userPermissionImporter.setSpaceAttachments_Add(null, newSpace);
        if (restrictions.attachments_delete)
            userPermissionImporter.setSpaceAttachments_Delete(null, newSpace);
        if (restrictions.restrictions_addDelete)
            userPermissionImporter.setSpaceRestrictions_AddDelete(null, newSpace);
        if (restrictions.mail_delete)
            userPermissionImporter.setSpaceMail_Delete(null, newSpace);
        if (restrictions.space_export)
            userPermissionImporter.setSpaceSpace_Export(null, newSpace);
        if (restrictions.space_admin)
            userPermissionImporter.setSpaceSpace_Admin(null, newSpace);
    }

    private void checkCurrentSpaceForNull() {
        if (newSpace == null)
            throw new RuntimeException("Property newSpace can't be null when calling this method. Use createSpaces() first!");
    }
}
