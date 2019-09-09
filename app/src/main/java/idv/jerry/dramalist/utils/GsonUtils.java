package idv.jerry.dramalist.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.data.ResponseData;

public class GsonUtils {

    public static ResponseData<DramaData> parseJson(String json){
        Gson gson = new Gson();

        ResponseData<DramaData> responseData = gson.fromJson(json,  new TypeToken<ResponseData<DramaData>>(){}.getType());
        return responseData;
    }
}
