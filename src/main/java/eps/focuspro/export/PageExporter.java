package eps.focuspro.export;


import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.UserAccessor;
import eps.focuspro.test_data_dtos.TestPage;
import eps.focuspro.test_data_dtos.TestSpace;

import java.util.ArrayList;
import java.util.List;


public class PageExporter {

    private PageManager pageManager;
    private AttachmentManager attachmentManager;
    private String testDataRootPath;

    public PageExporter(PageManager pageManager, AttachmentManager attachmentManager, String testDataRootPath) {
        this.pageManager = pageManager;
        this.attachmentManager = attachmentManager;
        this.testDataRootPath = testDataRootPath;
    }


    public List<TestPage> createTestPages(Space currentTestSpace, UserAccessor userAccessor) {
        List<Page> testPageList = getAllPagesOfSpace(currentTestSpace);
        TestSpace testSpace = new TestSpace();
        testSpace.testPages = new ArrayList<TestPage>();
        for (Page currentTestPage : testPageList) {
            TestPage testPage = createTestPageDtos(currentTestPage, currentTestSpace, userAccessor);
            testSpace.testPages.add(testPage);
        }
        return testSpace.testPages;
    }

    private TestPage createTestPageDtos(Page currentTestPage, Space currentTestSpace, UserAccessor userAccessor) {
        TestPage testPage = new TestPage();
        testPage.pageTitle = currentTestPage.getDisplayTitle();
        testPage.labels = LabelExporter.getTestPageLabels(currentTestPage);
        testPage.storageFormat = currentTestPage.getBodyAsString();
        testPage.pagePosition = currentTestPage.getPosition();
        testPage.isHomePage = currentTestPage.isHomePage();
        testPage.children = getChildrenTitles(currentTestPage);
        AttachmentExporter attachmentExporter = new AttachmentExporter(attachmentManager, testDataRootPath);
        attachmentExporter.exportAttachments(currentTestPage, currentTestSpace, testPage);
        PagePermissionExporter pagePermissionExporter = new PagePermissionExporter(userAccessor);
        testPage.pagePermissions = pagePermissionExporter.exportPagePermissions(currentTestPage);
        return testPage;
    }

    private List<String> getChildrenTitles(Page rootPage) {
        List<String> childrenTitleList = new ArrayList<String>();
        for (Page children : rootPage.getChildren()) {
            childrenTitleList.add(children.getDisplayTitle());
        }
        return childrenTitleList;
    }


    private List<Page> getAllPagesOfSpace(Space space) {
        return pageManager.getPages(space, true);
    }

}
