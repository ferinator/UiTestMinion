package eps.focuspro.Import;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import eps.focuspro.test_data_dtos.TestPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cew on 11/7/2016.
 */
public class SpacePageTree {
    private List<TestPage> unsortedPageTree;
    private List<TestPage> sortedPageTree;

    public SpacePageTree(List<TestPage> testPages) {
        unsortedPageTree = testPages;
        sortedPageTree = sortPages(testPages);
    }

    public List<TestPage> getSortedPageTree() {
        return sortedPageTree;
    }

    public TestPage getHomePage() {
        return sortedPageTree.get(0);
    }

    private List<TestPage> sortPages(List<TestPage> testPages) {
        if (unsortedPageTree.size() <= 1) {
            return unsortedPageTree;
        }
        List<TestPage> sortedPages = new ArrayList<>(testPages.size());

        TestPage homePage = testPages
                .stream()
                .filter(testPage -> Objects.equal(testPage.isHomePage, true))
                .findFirst()
                .get();
        sortedPages.add(homePage);

        sortedPages.addAll(sortDescendants(homePage));

        return sortedPages;
    }

    private List<TestPage> sortDescendants(TestPage homePage) {
        List<TestPage> pagesInLevel = getSortedChildren(homePage);
        List<TestPage> sortedDescendants = new ArrayList<>(pagesInLevel);

        while (pagesInLevel.size() != 0) {
            List<TestPage> pagesInNextLevel = new ArrayList<>();

            for (TestPage page : pagesInLevel) {
                if (page.children != null && page.children.size() > 0) {
                    List<TestPage> children = getSortedChildren(page);
                    int position = sortedDescendants.indexOf(page) + 1;

                    sortedDescendants.addAll(position, children);
                    pagesInNextLevel.addAll(children);
                }
            }

            pagesInLevel = new ArrayList<>(pagesInNextLevel);
        }

        return sortedDescendants;
    }

    private List<TestPage> getSortedChildren(TestPage rootPage) {
        List<TestPage> children = getTestPages(rootPage.children);

        if (positionIsSet(children.get(0))) {
        return children
                    .stream()
                    .sorted((testPage1, testPage2) -> testPage1.pagePosition.compareTo(testPage2.pagePosition))
                    .collect(Collectors.toList());
        } else {
        return children
                    .stream()
                    .sorted((testPage1, testPage2) -> testPage1.pageTitle.compareTo(testPage2.pageTitle))
                    .collect(Collectors.toList());
        }
    }

    private boolean positionIsSet(TestPage testPage) {
        return testPage.pagePosition != null;
    }

    private List<TestPage> getTestPages(List<String> pageTitles) {
        List<TestPage> testPages = new ArrayList<>(pageTitles.size());
        testPages.addAll(pageTitles.stream().map(this::getTestPage).collect(Collectors.toList()));
        return testPages;
    }

    private TestPage getTestPage(String pageTitle) {
        return unsortedPageTree.stream().filter(testPage -> testPage.pageTitle.equals(pageTitle)).findFirst().get();
    }
}
