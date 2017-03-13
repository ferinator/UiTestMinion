package eps.focuspro.Import;

import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.labels.Labelable;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;
import eps.focuspro.test_data_dtos.*;

import java.util.*;


public class PageImporter {

    private SpaceManager spaceManager;
    private PageManager pageManager;
    private Space space;
    private LabelManager labelManager;
    private AttachmentManager attachmentManager;
    private UserAccessor userAccessor;
    private ContentPermissionManager contentPermissionManager;
    private String testDataRootPath;


    public PageImporter(SpaceManager spaceManager,
                        PageManager pageManager,
                        Space space,
                        LabelManager labelManager,
                        AttachmentManager attachmentManager,
                        UserAccessor userAccessor,
                        ContentPermissionManager contentPermissionManager,
                        String testDataRootPath) {
        this.spaceManager = spaceManager;
        this.pageManager = pageManager;
        this.space = space;
        this.labelManager = labelManager;
        this.attachmentManager = attachmentManager;
        this.userAccessor = userAccessor;
        this.contentPermissionManager = contentPermissionManager;
        this.testDataRootPath = testDataRootPath;

    }

    public Page createPages(List<TestPage> testPages) {
        //Todo: FEK Should Content permmissions be Static Or Not ? GUS: why not both?
        PagePermissionImporter pagePermissionImporter = new PagePermissionImporter(userAccessor, contentPermissionManager);
        SpacePageTree testPageTree = new SpacePageTree(testPages);
        List<TestPage> sortedPages = testPageTree.getSortedPageTree();
        List<Page> newPages = new ArrayList<>(sortedPages.size());

        for (TestPage testPage : sortedPages) {
            Page newPage = new Page();
            TestPage parentTestPage = getParent(sortedPages, testPage);

            newPage.setSpace(space);
            newPage.setTitle(testPage.pageTitle);
            newPage.setBodyAsString(testPage.storageFormat);
            newPage.setPosition(testPage.pagePosition);
            pageManager.saveContentEntity(newPage, null);

            Page parentPage = getPage(newPages, parentTestPage);
            if (parentPage != null) {

                newPage.setParentPage(parentPage);
            }

            if (testPage.labels != null) {
                setLabels(testPage, newPage);
            }

            pagePermissionImporter.setPermissions(testPage.pagePermissions, newPage);

            if (testPage.attachments != null) {
                AttachmentImporter attachmentImporter = new AttachmentImporter(attachmentManager, labelManager);
                attachmentImporter.importAttachments(newPage, testPage.attachments);
            }
            newPages.add(newPage);
        }

        return newPages.get(0);
    }

    public static TestPage getParent(List<TestPage> testPages, TestPage page) {
        int pagePosition = getPosition(testPages, page);

        for (int i = pagePosition - 1; i >= 0; i--) {
            TestPage testPage = testPages.get(i);
            if (testPage.children != null && testPages.get(i).children.size() > 0) {
                for (String pageTitle : testPage.children) {
                    if (pageTitle.equals(page.pageTitle)) {
                        return testPage;
                    }
                }
            }
        }
        return null;
    }

    private static int getPosition(List<TestPage> testPages, TestPage page) {
        TestPage pageInList;
        for (int i = 0; i < testPages.size(); i++) {
            pageInList = testPages.get(i);
            if (pageInList.pageTitle.equals(page.pageTitle))
                return i;
        }
        return -1;
    }

    private static Page getPage(List<Page> pages, TestPage testpage) {
        for (Page page : pages) {
            if (page.getDisplayTitle().equals(testpage.pageTitle))
                return page;
        }
        return null;
    }

    private void setParent(Page currentPage, TestPage testPage, Map<Integer, Page> pages) {

    }

    private void setAttachments(Page currentPage, TestPage testpage) {
        for (TestAttachment currentAttachment : testpage.attachments) {
            com.atlassian.confluence.pages.Attachment attachment = new com.atlassian.confluence.pages.Attachment();
        }
    }

    private void setLabels(TestPage testpage, Labelable currentPage) {
        for (String testLabel : testpage.labels) {
            Label label = new Label(testLabel);
            labelManager.addLabel(currentPage, label);
        }
    }
}