package eps.focuspro;

import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.crowd.exception.DirectoryInstantiationException;
import eps.focuspro.error.UiException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileUtils {
    public static final String TestDataProjectPath = "\\src\\test\\resources\\testdata\\";
    public static String AttachmentRootPath;

    public static String getTestData(String testDataRootPath) { //TODO don: Sollten die Slashes in testDataRootPath noch mal geprüft werden?
        String filePath = testDataRootPath + TestDataProjectPath + "data.json";


        if (testDataExists(filePath)) {
            return getDataAsString(filePath);
        } else {
            throw new UiException("No test data in project root path \"" + testDataRootPath + "\"");
        }
    }

    private static boolean testDataExists(String testDataFilePath) {
        return new File(testDataFilePath).isFile();
    }

    private static String getDataAsString(String filePath) {
        String testData = "";
        try {
            InputStream is = new FileInputStream(filePath);
            testData = IOUtils.toString(is);
        } catch (IOException ex) {
            throw new UiException("Error reading test data json file!", ex);
        }

        return testData;
    }

    public static void createDirIfNotAlreadyExisting(File directory) {
        System.out.println("dir exists: " + directory.exists());
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdir();
            if (!dirCreated) {
                throw new UiException("", new DirectoryInstantiationException());
            }
        }
    }

    public static void cleanDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            if (!org.apache.commons.io.FileUtils.deleteQuietly(directory)) {
                throw new UiException("Couldn't delete directory '" + directory + "'.", new RuntimeException());
            }
        }
        if (!directory.mkdir()) {
            throw new UiException("Couldn't create directory '" + directory + "'.", new RuntimeException());
        }

    }
}

