package io.github.brightloong.week02.io.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;

/**
 * @author BrightLoong
 * @date 2021/1/18 20:34
 * @description
 */
public class OkHttpUtils {

    public static OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            return Objects.isNull(body) ? "" : body.string();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(get("http://localhost:8801"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
