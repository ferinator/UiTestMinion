package eps.focuspro.rest;

import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.security.ContentPermission;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;
import eps.focuspro.*;
import eps.focuspro.Import.Importer;
import eps.focuspro.error.UiException;
import eps.focuspro.export.Exporter;
import eps.focuspro.rest.response.ResponseFactory;
import eps.focuspro.rest.response.ResponseType;
import eps.focuspro.test_data_dtos.TestData;


public class Initializer {

    public static String importer(String testDataRootPath,
                                  SpaceManager spaceManager,
                                  PageManager pageManager,
                                  UserAccessor userAccessor,
                                  SpacePermissionManager spacePermissionManager,
                                  LabelManager labelManager,
                                  AttachmentManager attachmentManager,
                                  ContentPermissionManager contentPermissionManager) {
        try {
            String testDataStr = FileUtils.getTestData(testDataRootPath);
            TestData testData = JsonUtils.jsonToTestData(testDataStr);

            if (testDataRootPath == null) {
                testDataRootPath = "C:/Testminionattachment/";
            }
            FileUtils.AttachmentRootPath = testDataRootPath;

            Importer importer = new Importer(testDataRootPath, spaceManager, pageManager, userAccessor, spacePermissionManager, labelManager, attachmentManager, contentPermissionManager);
            importer.Import(testData);

            return ResponseFactory.create(ResponseType.SUCCESS, "Imported test spaces.");
        } catch (Exception ex) {
            return handleError(ex);
        }
    }

    //Liste mit allen Spaces holen
    //Test Spaces heraus filtern
    //Daten aus Test Spaces in TestData schreiben
    //Alle Pages mit allen entsprechenden Daten in TestData schreiben (Attachmentfiles auf dem Host abspeichern)
    //Das selbe f�r TestUsers
    //Das selbe f�r TestGroups
    //In JSON konvertieren
    //Als File abspeichern
    public static String exporter(String testDataRootPath,
                                  SpaceManager spaceManager,
                                  PageManager pageManager,
                                  SpacePermissionManager spacePermissionManager,
                                  UserAccessor userAccessor,
                                  AttachmentManager attachmentManager) {
        try {
            System.out.println("WORK MINION, WORK ---------- DO THE EXPORT");
            if (testDataRootPath == null) {
//                testDataRootPath = "C:/Testminionattachment/";
                throw new UiException("Query param 'testDataRootPath' can't be null!");
            }

            FileUtils.AttachmentRootPath = testDataRootPath;

            Exporter exporter = new Exporter(testDataRootPath, spaceManager, pageManager, userAccessor, spacePermissionManager, attachmentManager);
            exporter.export();
            return ResponseFactory.create(ResponseType.SUCCESS, "MAKE CONFLUENCE GREAT AGAIN");
        } catch (Exception ex) {
            return handleError(ex);
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private static String handleError(Exception ex) {
        UiException uiEx = as(UiException.class, ex);
        if (uiEx != null) {
            String errorMessage = uiEx.getMessage();
            if (uiEx.getCause() != null)
                errorMessage = errorMessage + " </br></br>Message: " + uiEx.getCause().getMessage() + " </br>Cause: " + uiEx.getCause().getCause();
            Logger.logError(uiEx.getMessage(), uiEx.getCause());
            return ResponseFactory.create(ResponseType.UI_ERROR, errorMessage); //Todo don: Message doesn't get logged right in JS!
        }

        Logger.logError(ex);
        String errorMessage = "Minion reports an unexpected error: " + ex.getMessage() + ". For further details check confluence log-file.";
        return ResponseFactory.create(ResponseType.UNKNOWN_ERROR, errorMessage);
    }

    protected static <T> T as(Class<T> t, Object o) { //TODO was changed
        return t.isInstance(o) ? t.cast(o) : null;
    }
}

