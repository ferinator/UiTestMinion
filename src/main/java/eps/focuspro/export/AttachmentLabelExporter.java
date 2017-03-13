package eps.focuspro.export;

import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.Page;

import java.util.ArrayList;
import java.util.List;


public class AttachmentLabelExporter {

    public static List<String> getAttachmentLabels(Attachment attachment) {
        List<Label> labelList = attachment.getLabels();
        List<String> labelStringList = new ArrayList<String>();
        for (Label label : labelList) {
            labelStringList.add(label.toString());
        }
        return labelStringList;
    }
}
