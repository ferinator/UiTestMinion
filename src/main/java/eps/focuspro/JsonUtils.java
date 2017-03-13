package eps.focuspro;

import com.google.gson.Gson;
import eps.focuspro.test_data_dtos.TestData;
import eps.focuspro.test_data_dtos.TestSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonUtils {
    private final static Gson gson = new Gson();

    public static TestData jsonToTestData(String testDataStr) {
        return gson.fromJson(testDataStr, TestData.class);
    }
}
