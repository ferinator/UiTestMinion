package eps.focuspro;

import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.spaces.SpaceStatus;
import eps.focuspro.error.UiException;
import eps.focuspro.test_data_dtos.TestData;
import eps.focuspro.test_data_dtos.TestSpace;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// VALIDATOR WIRD MOMENTAN NICH GENUTZT, DA VOR DEM IMPORTIEREN ALLE TEST SPACES GELÖSCHT WERDEN!
// ER WIRD ABER ERHALTEN, SOLLTE SICH DIESE FUNKTIONALITÄT ÄNDERN!
public class Validator {
    private TestData testData;
    private SpaceManager spaceManager;

    public Validator(TestData testData, SpaceManager spaceManager) {
        this.testData = testData;
        this.spaceManager = spaceManager;
    }

    public void ensureThereWontBeDuplicateSpaces() {
        List<String> confluenceSpaceKeys = new ArrayList<String>(spaceManager.getAllSpaceKeys(SpaceStatus.CURRENT));

        HashSet<String> duplicateSpaceKeys = findDuplicates(ListUtils.union(confluenceSpaceKeys, getTestDataSpaceKeys()));
        HashSet<String> falseSpaceNames = evaluateFalseSpaceNames(getConfluenceSpaceNames());

        if (!duplicateSpaceKeys.isEmpty() || !falseSpaceNames.isEmpty())
            throw new UiException("Can't import test spaces because test spaces already exist."); //TODO don: Error Ausgabe erweitern dass auch Listen in einer Flag angezeigt werden können.
    }

    private List<String> getTestDataSpaceKeys() {
        List<String> spaceKeys = new ArrayList<String>();

        for (TestSpace testSpace : testData.testSpaces)
            spaceKeys.add(testSpace.spaceKey);

        return spaceKeys;
    }

    private List<String> getConfluenceSpaceNames() {
        List<String> confluenceSpaceNames = new ArrayList<String>();

        for (Space space : spaceManager.getAllSpaces())
            confluenceSpaceNames.add(space.getName());

        return confluenceSpaceNames;
    }

    private HashSet<String> evaluateFalseSpaceNames(List<String> confluenceSpaceNames) {
        final HashSet<String> falseSpaceNames = new HashSet<String>();

        for (String spaceName : confluenceSpaceNames) {
            if (spaceName.contains(Config.TestSpaceSignature))
                falseSpaceNames.add(spaceName);
        }

        return falseSpaceNames;
    }

    private HashSet<String> findDuplicates(List<String> possibleDuplicates) {
        final HashSet<String> duplicates = new HashSet<String>();
        final HashSet<String> validationSet = new HashSet<String>();

        for (String yourInt : possibleDuplicates)
        {
            if (!validationSet.add(yourInt))
            {
                duplicates.add(yourInt);
            }
        }
        return duplicates;
    }
}
