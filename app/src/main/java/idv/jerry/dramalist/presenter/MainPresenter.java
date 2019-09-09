package idv.jerry.dramalist.presenter;

import android.content.Context;

import java.util.ArrayList;

import idv.jerry.dramalist.activity.MainView;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.data.ResponseData;
import idv.jerry.dramalist.model.DramaModel;
import idv.jerry.dramalist.utils.GsonUtils;

public class MainPresenter {

    private DramaModel dramaModel;
    private MainView callback;
    private Context context;
    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        dramaModel = new DramaModel(context);
        callback = mainView;
    }

    public void fetchDramaList (){

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<DramaData> dramaList = dramaModel.fetchDramaList();
                        callback.updateDramaRecyclerView(dramaList);
                    }
                }
        ).start();

    }

    public void getDramaListFromJson(String json) {
        ResponseData<DramaData> responseData = GsonUtils.parseJson(json);
        if (responseData != null){
            callback.updateDramaRecyclerView(responseData.data);
        }
    }

    public ArrayList<DramaData> getDreamList(){
        ArrayList<DramaData> tmpList = dramaModel.getDramaList();
        return tmpList;
    }

    public void destroy(){

        if (dramaModel != null){
            dramaModel.destroy();
        }
        dramaModel = null;
        callback = null;
    }
}
