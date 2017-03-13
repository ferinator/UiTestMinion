package ut.eps.focuspro;

import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.spaces.SpaceStatus;
import eps.focuspro.Config;
import eps.focuspro.Validator;
import eps.focuspro.error.UiException;
import eps.focuspro.test_data_dtos.TestData;
import eps.focuspro.test_data_dtos.TestSpace;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cew on 11/1/2016.
 */
public class ValidatorTest {
    private static SpaceManager spaceManager;
    private static SpaceManager spaceManagerWithTestSpaces;
    private static SpaceManager spaceManagerWithTestSpaceName;
    private static TestData testData;
    private static TestSpace uiTestSpace1;
    private static TestSpace uiTestSpace2;

    @BeforeClass
    public static void init() {
        uiTestSpace1 = new TestSpace();
        uiTestSpace1.spaceKey = "PTCTCPT"; //TODO don: Korrekte Signatur eintragen
        uiTestSpace1.spaceName = Config.TestSpaceSignature + " Create Page Tree"; //TODO don: Korrekte Signatur eintragen

        uiTestSpace2 = new TestSpace();
        uiTestSpace2.spaceKey = "PTCTTR"; //TODO don: Korrekte Signatur eintragen
        uiTestSpace2.spaceName = Config.TestSpaceSignature + " Tag Replacement"; //TODO don: Korrekte Signatur eintragen

        spaceManager = spaceManagerMockWithOutTestSpaces();
        spaceManagerWithTestSpaces = spaceManagerMockWithTestSpaces();
        spaceManagerWithTestSpaceName = spaceManagerMockWithOutTestSpacesButTestSpaceName();

        testData = new TestData();
        testData.testSpaces = new ArrayList<TestSpace>(Arrays.asList(uiTestSpace1, uiTestSpace2));
    }

    private static SpaceManager spaceManagerMockWithTestSpaces() {
        Space space1 = spaceMock("ABC", "ABC Space");
        Space space2 = spaceMock(uiTestSpace1.spaceKey, uiTestSpace1.spaceName);
        Space space3 = spaceMock(uiTestSpace2.spaceKey, uiTestSpace2.spaceName);
        Collection<String> spaceKeys = new HashSet<String>(Arrays.asList(space1.getKey(), space2.getKey(), space3.getKey()));
        List<Space> spaceList = new ArrayList<Space>(Arrays.asList(space1, space2, space3));

        SpaceManager spaceManager = mock(SpaceManager.class);
        when(spaceManager.getAllSpaceKeys(SpaceStatus.CURRENT)).thenReturn(spaceKeys);

        return spaceManager;
    }

    private static SpaceManager spaceManagerMockWithOutTestSpaces() {
        Space space1 = spaceMock("ABC", "ABC Space");
        Space space2 = spaceMock("CDE", "CDE Project Space");
        Space space3 = spaceMock("TRG", "TRG Product Space");
        Collection<String> spaceKeys = new HashSet<String>(Arrays.asList(space1.getKey(), space2.getKey(), space3.getKey()));
        List<Space> spaceList = new ArrayList<Space>(Arrays.asList(space1, space2, space3));

        SpaceManager spaceManager = mock(SpaceManager.class);
        when(spaceManager.getAllSpaceKeys(SpaceStatus.CURRENT)).thenReturn(spaceKeys);
        when(spaceManager.getAllSpaces()).thenReturn(spaceList);

        return spaceManager;
    }

    private static SpaceManager spaceManagerMockWithOutTestSpacesButTestSpaceName() {
        Space space1 = spaceMock("ABC", "ABC Space");
        Space space2 = spaceMock("CDE", "CDE Project Space");
        Space space3 = spaceMock("TRG", Config.TestSpaceSignature + " TRG Product Space");
        Collection<String> spaceKeys = new HashSet<String>(Arrays.asList(space1.getKey(), space2.getKey(), space3.getKey()));
        List<Space> spaceList = new ArrayList<Space>(Arrays.asList(space1, space2, space3));

        SpaceManager spaceManager = mock(SpaceManager.class);
        when(spaceManager.getAllSpaceKeys(SpaceStatus.CURRENT)).thenReturn(spaceKeys);
        when(spaceManager.getAllSpaces()).thenReturn(spaceList);

        return spaceManager;
    }

    private static Space spaceMock(String spaceKey, String spaceName) {
        Space space = mock(Space.class);
        when(space.getKey()).thenReturn(spaceKey);
        when(space.getName()).thenReturn(spaceName);


        return space;
    }



    @Test
    public void noTestSpacesInConfluenceTest() {
        Validator validator = new Validator(testData, spaceManager);
        validator.ensureThereWontBeDuplicateSpaces();
    }

    @Test(expected = UiException.class)
    public void testSpacesInConfluenceTest() {
        Validator validator = new Validator(testData, spaceManagerWithTestSpaces);
        validator.ensureThereWontBeDuplicateSpaces();
    }

    @Test(expected = UiException.class)
    public void spaceHasTestSpaceSignatureInSpaceNameInConfluenceTest() {
        Validator validator = new Validator(testData, spaceManagerWithTestSpaceName);
        validator.ensureThereWontBeDuplicateSpaces();
    }
}
