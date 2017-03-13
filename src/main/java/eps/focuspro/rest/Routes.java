package eps.focuspro.rest;


import com.atlassian.confluence.core.ContentPermissionManager;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.security.ContentPermission;
import com.atlassian.confluence.security.DefaultSpacePermissionManager;
import com.atlassian.confluence.security.PermissionManager;
import com.atlassian.confluence.security.SpacePermissionManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;
import eps.focuspro.rest.response.ResponseFactory;
import eps.focuspro.rest.response.ResponseType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("minion")
@Produces({MediaType.APPLICATION_JSON})
public class Routes {
    private SpaceManager spaceManager;
    private PageManager pageManager;
    private LabelManager labelManager;
    private AttachmentManager attachmentManager;
    private UserAccessor userAccessor;
    private SpacePermissionManager spacePermissionManager;
    private ContentPermissionManager contentPermissionManager;


    public Routes(SpaceManager spaceManager,
                  PageManager pageManager,
                  LabelManager labelManager,
                  AttachmentManager attachmentManager,
                  UserAccessor userAccessor,
                  SpacePermissionManager spacePermissionManager,
                  ContentPermissionManager contentPermissionManager) {
        this.spaceManager = spaceManager;
        this.pageManager = pageManager;
        this.spacePermissionManager = spacePermissionManager;
        this.labelManager = labelManager;
        this.attachmentManager = attachmentManager;
        this.userAccessor = userAccessor;
        this.spacePermissionManager = spacePermissionManager;
        this.contentPermissionManager = contentPermissionManager;
    }

    @GET
    @Path("/import")
    public Response importer(@QueryParam("testDataRootPath") String testDataRootPath) {
        String response = Initializer.importer(testDataRootPath, spaceManager, pageManager, userAccessor, spacePermissionManager,labelManager, attachmentManager, contentPermissionManager);
        return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/export")
    public Response exporter(@QueryParam("testDataRootPath") String testDataRootPath) {
        String response = Initializer.exporter(testDataRootPath, spaceManager, pageManager, spacePermissionManager, userAccessor, attachmentManager);
        return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/test")
    public Response tester(@QueryParam("testDataRootPath") String testDataRootPath) {
        System.out.println("pageId: " + testDataRootPath);
        Page page = pageManager.getPage(Long.parseLong(testDataRootPath));
        System.out.println("VIEW Permissions: " + contentPermissionManager.getContentPermissionSets(page, ContentPermission.VIEW_PERMISSION));
        System.out.println("EDIT Permissions: " + contentPermissionManager.getContentPermissionSets(page, ContentPermission.EDIT_PERMISSION));

        String resp = ResponseFactory.create(ResponseType.SUCCESS, "It worked: " + testDataRootPath);
        System.out.println("Response: " + resp);
        return Response.ok(resp, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
