package eps.focuspro.export;


import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.spaces.Space;
import eps.focuspro.FileUtils;
import eps.focuspro.test_data_dtos.TestAttachment;
import eps.focuspro.test_data_dtos.TestPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static eps.focuspro.FileUtils.AttachmentRootPath;
import static eps.focuspro.FileUtils.TestDataProjectPath;


public class AttachmentExporter {

    private AttachmentManager attachmentManager;
    private String testDataRootPath;

    public AttachmentExporter(AttachmentManager attachmentManager,
                              String testDataRootPath) {
        this.attachmentManager = attachmentManager;
        this.testDataRootPath = testDataRootPath;

    }

    public void exportAttachments(Page page, Space space, TestPage testPage) {
        String urlPrefix = "http://localhost:8090";
        List<Attachment> attachments = attachmentManager.getAllVersionsOfAttachments(page); //Todo
        List<TestAttachment> testAttachmentList = new ArrayList<TestAttachment>();
        for (Attachment attachment : attachments) {
            createAttachmentDirectories(page, space, attachment);
            AttachmentDownloader attachmentDownloader = new AttachmentDownloader(testDataRootPath);
            attachmentDownloader.downloadAttachments(urlPrefix, attachment, space, page);
            testAttachmentList.add(createAttachmentDto(attachment, space, page));
        }
        testPage.attachments = testAttachmentList;
    }

    private TestAttachment createAttachmentDto(Attachment attachment, Space currentSpace, Page currentPage) {
        TestAttachment testAttachment = new TestAttachment();
        testAttachment.id = attachment.getId();
        testAttachment.name = attachment.getDisplayTitle();
        testAttachment.filepath = (currentSpace.getKey() + "\\" + currentPage.getDisplayTitle());
        testAttachment.fileName = attachment.getFileName();
        testAttachment.creator = attachment.getCreator().getName();
        testAttachment.attachmentLabels = AttachmentLabelExporter.getAttachmentLabels(attachment);
        testAttachment.mediaType = attachment.getMediaType();
        testAttachment.comment = attachment.getVersionComment();
        testAttachment.fileSize = attachment.getFileSize();
        testAttachment.version = attachment.getVersion();
        attachment.getVersionComment();
        return testAttachment;
    }


    private void createAttachmentDirectories(Page currentTestPage, Space currentTestSpace, Attachment attachment) {
        createSubDirectories(AttachmentRootPath, currentTestSpace.getKey(), getPageName(currentTestPage), getAttachmentVersion(attachment));
    }

    public static String getOutputFilePath(Page currentTestPage, Attachment attachment) {
        String fileName = getAttachmentFileName(attachment);
        String spaceKey = attachment.getSpaceKey();
        String pageName = getPageName(currentTestPage);
        String attachmentVersion = Integer.toString(getAttachmentVersion(attachment));
        return AttachmentRootPath + FileUtils.TestDataProjectPath + "attachments\\"+ spaceKey + "\\" + pageName + "\\" + attachmentVersion + "\\" + fileName;
    }

    private static void createSubDirectories(String attachmentRootPath, String spaceKey, String pageName, int attachmentVersion) {
        File spaceDir = new File(attachmentRootPath + FileUtils.TestDataProjectPath + "\\attachments\\" + spaceKey);
        FileUtils.createDirIfNotAlreadyExisting(spaceDir);

        File pageDir = new File(spaceDir.toString() + "\\" + pageName);
        FileUtils.createDirIfNotAlreadyExisting(pageDir);

        File versionDir = new File(pageDir.toString() + "\\" + attachmentVersion);
        FileUtils.createDirIfNotAlreadyExisting(versionDir);
    }

    private static String getAttachmentFileName(Attachment attachment) {
        return attachment.getFileName();
    }

    private static String getPageName(Page currentTestPage) {
        return currentTestPage.getDisplayTitle();
    }

    private static int getAttachmentVersion(Attachment attachment) {
        return attachment.getVersion();
    }

}
