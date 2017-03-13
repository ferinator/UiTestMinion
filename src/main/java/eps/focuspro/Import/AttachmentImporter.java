package eps.focuspro.Import;

import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.labels.Labelable;
import com.atlassian.confluence.labels.Labelling;
import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.SavableAttachment;
import com.atlassian.confluence.pages.attachments.AttachmentDataStreamSizeMismatchException;
import com.atlassian.gzipfilter.org.tuckey.web.filters.urlrewrite.Run;
import eps.focuspro.FileUtils;
import eps.focuspro.Logger;
import eps.focuspro.error.UiException;
import eps.focuspro.export.Exporter;
import eps.focuspro.test_data_dtos.TestAttachment;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AttachmentImporter {

    private AttachmentManager attachmentManager;
    private LabelManager labelManager;

    public AttachmentImporter(AttachmentManager attachmentManager, LabelManager labelManager) {
        this.attachmentManager = attachmentManager;
        this.labelManager = labelManager;
    }

    public void importAttachments(Page newPage, List<TestAttachment> testAttachments) {
        createAttachments(newPage, testAttachments);
    }

    private void createAttachments(Page newPage, List<TestAttachment> testAttachments) {
        List<SavableAttachment> savableAttachments = new ArrayList<>();

        for (TestAttachment testAttachment : testAttachments) {
            Attachment attachment = new Attachment(testAttachment.name, testAttachment.mediaType, testAttachment.fileSize, testAttachment.comment);
            attachment.setVersion(testAttachment.version);
            newPage.addAttachment(attachment);
            addAttachmentLabels(attachment, testAttachment);


            String attachmentPath = FileUtils.AttachmentRootPath + FileUtils.TestDataProjectPath + "attachments\\" + testAttachment.filepath + "\\" + testAttachment.version + "\\" + testAttachment.fileName;
            try {
                InputStream input = new FileInputStream(attachmentPath);
                savableAttachments.add(new SavableAttachment(attachment, null, input));
            } catch (Exception e) {
                throw new UiException("Something went wrong importing an attachment.", e);
            }
        }

        uploadAttachments(savableAttachments);
    }

    private void addAttachmentLabels(Attachment attachment, TestAttachment testAttachment) {
        for (String testLabel : testAttachment.attachmentLabels) {
            Label label = new Label(testLabel);
            labelManager.addLabel((Labelable) attachment, label);
        }
    }

    private void uploadAttachments(List<SavableAttachment> savableAttachments) {
        try {
            attachmentManager.saveAttachments(savableAttachments);
        } catch (AttachmentDataStreamSizeMismatchException e){
            throw new UiException("Attachment file size does not match with MUTHAAAFUCKAAA", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            for (SavableAttachment savableAttachment : savableAttachments) {
                InputStream in = savableAttachment.getAttachmentData();
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}

