package ut.eps.focuspro;

import eps.focuspro.Import.SpacePageTree;
import eps.focuspro.test_data_dtos.TestPage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cew on 11/7/2016.
 */
public class SpacePageTreeTest {
    private List<TestPage> testPages;

    public SpacePageTreeTest() {
        testPages = SpacePageTreeMock.getPageTree();
    }


    @Test
    public void sortPageTreeTest() {
        SpacePageTree pageTree = new SpacePageTree(testPages);
        List<TestPage> testPages = pageTree.getSortedPageTree();
        Assert.assertEquals(SpacePageTreeMock.testPageName1, testPages.get(0).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName2, testPages.get(1).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName3, testPages.get(2).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName4, testPages.get(3).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName5, testPages.get(4).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName6, testPages.get(5).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName7, testPages.get(6).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName10, testPages.get(7).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName9, testPages.get(8).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName8, testPages.get(9).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName11, testPages.get(10).pageTitle);
        Assert.assertEquals(SpacePageTreeMock.testPageName12, testPages.get(11).pageTitle);
    }

    @Test
    public void sortEmptyPageTreeTest() {
        SpacePageTree pageTree = new SpacePageTree(new ArrayList<TestPage>());
        List<TestPage> testPages = pageTree.getSortedPageTree();
        Assert.assertEquals(0, testPages.size());
    }

}

