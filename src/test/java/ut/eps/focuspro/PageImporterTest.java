package ut.eps.focuspro;


import eps.focuspro.Import.PageImporter;
import eps.focuspro.test_data_dtos.TestPage;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PageImporterTest {
    private List<TestPage> testPages;

    public PageImporterTest() {
        testPages = SpacePageTreeMock.getSortedPageTree();
    }


    @Test
    public void getParentOfHomePageTest() {
        TestPage parentPage = PageImporter.getParent(testPages, testPages.get(0));

        Assert.assertNull(parentPage);
    }

    @Test
    public void getParentPageTreeWithOnePageTest() {
        List<TestPage> pageTree = new ArrayList<>(1);
        pageTree.add(testPages.get(0));

        TestPage parentPage = PageImporter.getParent(pageTree, testPages.get(0));

        Assert.assertNull(parentPage);
    }

    @Test
    public void getParentOfChildPageTest() {
        TestPage parentPage = PageImporter.getParent(testPages, testPages.get(10));

        Assert.assertEquals(testPages.get(0), parentPage);
    }

    @Test
    public void getParentOfChildPageTest2() {
        TestPage parentPage = PageImporter.getParent(testPages, testPages.get(8));

        Assert.assertEquals(testPages.get(4), parentPage);
    }
}
