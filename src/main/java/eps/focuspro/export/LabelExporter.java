package eps.focuspro.export;

import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.pages.Page;

import java.util.ArrayList;
import java.util.List;


public class LabelExporter {

    public static List<String> getTestPageLabels(Page currentTestPage) {
        List<Label> labelList = currentTestPage.getLabels();
        List<String> labelStringList = new ArrayList<String>();
        for (Label label : labelList) {
            labelStringList.add(label.toString());
        }
        return labelStringList;
    }
}
