package eps.focuspro.Import;

import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.security.ContentPermission;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.crowd.search.ldap.NullResultException;
import eps.focuspro.error.UiException;
import eps.focuspro.test_data_dtos.PagePermissions;
import org.apache.commons.lang.NullArgumentException;

import java.util.*;
import java.util.stream.Collectors;


public class PagePermissionImporter {
    private UserAccessor userAccessor;
    private ContentPermissionManager contentPermissionManager;

    public PagePermissionImporter(UserAccessor userAccessor, ContentPermissionManager contentPermissionManager) {
        this.userAccessor = userAccessor;
        this.contentPermissionManager = contentPermissionManager;
    }

    public void setPermissions(PagePermissions pagePermissions, Page newPage) {
        addContentPermissions(getConfluenceUsers(pagePermissions.usersCanView), ContentPermission.VIEW_PERMISSION, newPage);
        addContentPermissions(getConfluenceUsers(pagePermissions.usersCanEdit), ContentPermission.EDIT_PERMISSION, newPage);
        addContentPermissions(pagePermissions.groupsCanView, ContentPermission.VIEW_PERMISSION, newPage);
        addContentPermissions(pagePermissions.groupsCanEdit, ContentPermission.EDIT_PERMISSION, newPage);
    }

    private <T> void addContentPermissions(List<T> members, String contentPermissionType, Page newPage) {
        if (members == null)
            throw new UiException("List of members can't be null. Use an empty list instead.", new NullArgumentException("members"));

        for (T member : members) {
            ContentPermission contentPermission = null;
            if (member instanceof String) {
                contentPermission = ContentPermission.createGroupPermission(contentPermissionType, (String) member);
            }
            if (member instanceof ConfluenceUser) {
                contentPermission = ContentPermission.createUserPermission(contentPermissionType, (ConfluenceUser) member);
            }
            if (contentPermission == null) {
                throw new UiException("Content permission is null for member '" + member + "' but has to be set!", new NullResultException());
            }

            contentPermissionManager.addContentPermission(contentPermission, newPage);
        }
    }

    private List<ConfluenceUser> getConfluenceUsers(List<String> userNames) {
        return userNames
                .stream()
                .map(userName -> userAccessor.getUserByName(userName))
                .collect(Collectors.toList());
    }
}
