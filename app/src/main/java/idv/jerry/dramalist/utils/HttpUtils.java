package idv.jerry.dramalist.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    public static final String ERROR_CODE = "-99";
    private static String URL = "http://www.mocky.io/v2/5a97c59c30000047005c1ed2";

    public static String getDramaList(){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e){
            e.printStackTrace();
            return ERROR_CODE;
        }
    }
}
