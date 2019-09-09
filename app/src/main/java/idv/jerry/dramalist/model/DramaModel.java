package idv.jerry.dramalist.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import idv.jerry.dramalist.config.AppConfig;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.data.ResponseData;
import idv.jerry.dramalist.utils.GsonUtils;
import idv.jerry.dramalist.utils.HttpUtils;
import idv.jerry.dramalist.utils.SharedPreferencesUtil;

public class DramaModel {

    String TAG = this.getClass().getCanonicalName();
    ArrayList<DramaData> dramaList;
    Context context;

    public DramaModel(Context context) {
        this.context = context;
        dramaList = new ArrayList<>();
    }

    public ArrayList<DramaData> fetchDramaList(){

        if (dramaList == null){
            dramaList = new ArrayList<>();
        }
        dramaList.clear();

        String json = HttpUtils.getDramaList();

        SharedPreferencesUtil.clearString(context, AppConfig.dataKey);
        SharedPreferencesUtil.putString(context, AppConfig.dataKey, json);

        Log.d(TAG, "getJson: " + json);

        ResponseData<DramaData> responseData = GsonUtils.parseJson(json);
        if (responseData != null && responseData.data != null){
            dramaList.addAll(responseData.data);
        }

        return dramaList;
    }

    public ArrayList<DramaData> getDramaList(){
        if (dramaList == null || dramaList.isEmpty()){
            dramaList = fetchDramaList();
        }
        return dramaList;
    }

    public void destroy(){
        if (dramaList != null){
            dramaList.clear();
        }

        context = null;
    }

}
