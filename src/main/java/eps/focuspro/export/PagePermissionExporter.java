package eps.focuspro.export;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.security.ContentPermission;
import com.atlassian.confluence.security.ContentPermissionSet;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.sal.api.user.UserKey;
import eps.focuspro.Config;
import eps.focuspro.test_data_dtos.PagePermissions;

import java.util.ArrayList;
import java.util.List;


public class PagePermissionExporter {


    private UserAccessor userAccessor;

    public PagePermissionExporter(UserAccessor userAccessor){
        this.userAccessor = userAccessor;
    }

    public PagePermissions exportPagePermissions(Page page) {
        ContentPermissionSet viewPermissionSet = page.getContentPermissionSet(ContentPermission.VIEW_PERMISSION);;
        ContentPermissionSet editPermissionSet = page.getContentPermissionSet(ContentPermission.EDIT_PERMISSION);;
        return createPagePermissionsDtos(editPermissionSet, viewPermissionSet);
    }

    private PagePermissions createPagePermissionsDtos(ContentPermissionSet editPermissionSet, ContentPermissionSet viewPermissionSet) {
        PagePermissions pagePermissions = new PagePermissions();

        pagePermissions.usersCanView = getTestUserNames(viewPermissionSet);
        pagePermissions.usersCanEdit = getTestUserNames(editPermissionSet);
        pagePermissions.groupsCanView = getTestGroupNames(viewPermissionSet);
        pagePermissions.groupsCanEdit = getTestGroupNames(editPermissionSet);

        return pagePermissions;
    }

    private List<String> getTestUserNames(ContentPermissionSet contentPermissionSet) {
        if (contentPermissionSet == null)
            return new ArrayList<>();

        List<String> testUserNames = new ArrayList<>();
        List<UserKey> userKeys = contentPermissionSet.getUserKeys();

        for (UserKey userKey : userKeys) {
            String userName = userAccessor.getUserByKey(userKey).getName();
            if (userName.contains(Config.TestUserSignature) || userName.equals(Config.AdminUserName)) {
                testUserNames.add(userName);
            }
        }

        return testUserNames;
    }

    private List<String> getTestGroupNames(ContentPermissionSet contentPermissionSet) {
        if (contentPermissionSet == null)
            return new ArrayList<>();

        List<String> testGroupNames = new ArrayList<>();
        List<String> groupNames = contentPermissionSet.getGroupNames();

        for (String groupName : groupNames) {
            if (groupName.contains(Config.TestGroupSignature) || groupName.equals(Config.AdminUserName)) {
                testGroupNames.add(groupName);
            }
        }

        return testGroupNames;
    }
}

