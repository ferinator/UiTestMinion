package eps.focuspro.rest.response;

import com.google.gson.Gson;

public class ResponseFactory {
    private final static Gson gson = new Gson();

    public static String create(final ResponseType type, final String data) {
        ResponseData resp = new ResponseData();
        resp.type = type;
        resp.data = data != null ? data : "";

        return gson.toJson(resp);
    }
}
