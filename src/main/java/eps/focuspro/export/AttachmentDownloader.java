package eps.focuspro.export;

import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.spaces.Space;
import eps.focuspro.Logger;
import eps.focuspro.error.UiException;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import eps.focuspro.FileUtils;

import static eps.focuspro.FileUtils.TestDataProjectPath;


public class AttachmentDownloader {

    private String testDataRootPath;

    public AttachmentDownloader(String testDataRootPath){
        this.testDataRootPath = testDataRootPath;
    }


    public void downloadAttachments(String urlPrefix, Attachment attachment, Space currentTestSpace, Page testPage) {



        String outputFilePath = AttachmentExporter.getOutputFilePath(testPage, attachment);
        String attachmentPath = getAttachmentPath(urlPrefix, attachment);

        try {
            URLConnection urlConnect = createURLConnection(attachmentPath);
            InputStream input = urlConnect.getInputStream();
            try {
                writeToFile(outputFilePath, input);
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    input.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void cleanAttachmentDirectory() {
        FileUtils.cleanDirectory(getAttachmentDirectoryPath());
    }

    private void writeToFile(String outputFilePath, InputStream input) {
        OutputStream output;
        try {
            output = getFileOutputStream(outputFilePath);
        } catch (FileNotFoundException ex) {
            throw new UiException("There was no file found in location '" + outputFilePath + "' while downloading attachments.", ex);
        }

        try {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private FileOutputStream getFileOutputStream(String outputFilePath) throws FileNotFoundException {
        return new FileOutputStream(outputFilePath);
    }

    private URL getURL(String attachmentPath) throws MalformedURLException {
        URL url = new URL(attachmentPath);
        return url;
    }

    private URLConnection createURLConnection(String attachmentPath) throws IOException {
        String auth = Base64.encodeBase64String("admin:admin".getBytes());
        URLConnection urlConnect = getURL(attachmentPath).openConnection();
        urlConnect.setDoInput(true);
        urlConnect.setDoOutput(true);
        urlConnect.setRequestProperty("Authorization", "Basic " + auth);
        return urlConnect;
    }


    private String getAttachmentPath(String urlPrefix, Attachment attachment) {
        return urlPrefix + attachment.getDownloadPath();
    }

    private String getAttachmentDirectoryPath() {
        return testDataRootPath + "\\" + TestDataProjectPath + "attachments";
    }
}
