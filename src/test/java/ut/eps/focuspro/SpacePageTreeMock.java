package ut.eps.focuspro;

import eps.focuspro.test_data_dtos.TestPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpacePageTreeMock {
    public static String testPageName1 = "Testpage 01";
    public static String testPageName2 = "Testpage 02";
    public static String testPageName3 = "Testpage 03";
    public static String testPageName4 = "Testpage 04";
    public static String testPageName5 = "Testpage 05";
    public static String testPageName6 = "Testpage 06";
    public static String testPageName7 = "Testpage 07";
    public static String testPageName8 = "Testpage 08";
    public static String testPageName9 = "Testpage 09";
    public static String testPageName10 = "Testpage 10";
    public static String testPageName11 = "Testpage 11";
    public static String testPageName12 = "Testpage 12";

    public static List<TestPage> getPageTree() {

        //testPage 1
        //--testPage 2
        //----testPage 3
        //----testPage 4
        //----testPage 5
        //--testPage 6
        //----testPage 7
        //------testPage 10
        //------testPage 9
        //------testPage 8
        //----testPage 11
        //----testPage 12

        TestPage testPage1 = createTestPage(testPageName1, null, new String[]{testPageName2, testPageName6}, true);
        TestPage testPage2 = createTestPage(testPageName2, null, new String[]{testPageName3, testPageName4, testPageName5}, false);
        TestPage testPage3 = createTestPage(testPageName3, null, new String[]{}, false);
        TestPage testPage4 = createTestPage(testPageName4, null, new String[]{}, false);
        TestPage testPage5 = createTestPage(testPageName5, null, new String[]{}, false);
        TestPage testPage6 = createTestPage(testPageName6, null, new String[]{testPageName12, testPageName7, testPageName11}, false);
        TestPage testPage7 = createTestPage(testPageName7, null, new String[]{testPageName8, testPageName9, testPageName10}, false);
        TestPage testPage8 = createTestPage(testPageName8, 2, new String[]{}, false);
        TestPage testPage9 = createTestPage(testPageName9, 1, new String[]{}, false);
        TestPage testPage10 = createTestPage(testPageName10, 0, new String[]{}, false);
        TestPage testPage11 = createTestPage(testPageName11, null, new String[]{}, false);
        TestPage testPage12 = createTestPage(testPageName12, null, new String[]{}, false);

        List<TestPage> testPages = new ArrayList<>();
        testPages.add(testPage8);
        testPages.add(testPage2);
        testPages.add(testPage12);
        testPages.add(testPage10);
        testPages.add(testPage5);
        testPages.add(testPage6);
        testPages.add(testPage3);
        testPages.add(testPage11);
        testPages.add(testPage1);
        testPages.add(testPage7);
        testPages.add(testPage4);
        testPages.add(testPage9);

        return testPages;
    }

    public static List<TestPage> getSortedPageTree() {
        //testPage 1
        //--testPage 2
        //----testPage 3
        //------testPage 4
        //--testPage 5
        //----testPage 6
        //------testPage 7
        //------testPage 8
        //----testPage 9
        //--testPage 10
        //--testPage 11
        //----testPage 12

        TestPage testPage1 = createTestPage(testPageName1, null, new String[]{testPageName2,testPageName5,testPageName10,testPageName11}, true);
        TestPage testPage2 = createTestPage(testPageName2, null, new String[]{testPageName3}, false);
        TestPage testPage3 = createTestPage(testPageName3, null, new String[]{testPageName4}, false);
        TestPage testPage4 = createTestPage(testPageName4, null, new String[]{}, false);
        TestPage testPage5 = createTestPage(testPageName5, null, new String[]{testPageName6,testPageName9}, false);
        TestPage testPage6 = createTestPage(testPageName6, null, new String[]{testPageName7,testPageName8}, false);
        TestPage testPage7 = createTestPage(testPageName7, null, new String[]{}, false);
        TestPage testPage8 = createTestPage(testPageName8, null, new String[]{}, false);
        TestPage testPage9 = createTestPage(testPageName9, null, new String[]{}, false);
        TestPage testPage10 = createTestPage(testPageName10, null, new String[]{}, false);
        TestPage testPage11 = createTestPage(testPageName11, null, new String[]{testPageName12}, false);
        TestPage testPage12 = createTestPage(testPageName12, null, new String[]{}, false);

        List<TestPage> testPages = new ArrayList<>();
        testPages.add(testPage1);
        testPages.add(testPage2);
        testPages.add(testPage3);
        testPages.add(testPage4);
        testPages.add(testPage5);
        testPages.add(testPage6);
        testPages.add(testPage7);
        testPages.add(testPage8);
        testPages.add(testPage9);
        testPages.add(testPage10);
        testPages.add(testPage11);
        testPages.add(testPage12);

        return testPages;
    }

    private static TestPage createTestPage(String title, Integer position, String[] children, boolean isHomepage) {
        TestPage testPage = new TestPage();
        testPage.pageTitle = title;
        testPage.children = new ArrayList<>(Arrays.asList(children));
        testPage.pagePosition = position;
        testPage.isHomePage = isHomepage;
        return testPage;
    }


}
