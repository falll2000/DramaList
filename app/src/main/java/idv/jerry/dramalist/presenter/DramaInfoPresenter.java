package idv.jerry.dramalist.presenter;

import android.content.Context;

import java.util.ArrayList;

import idv.jerry.dramalist.activity.DramaInfoView;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.model.DramaModel;

public class DramaInfoPresenter {

    private DramaModel dramaModel;
    private DramaInfoView callback;

    public DramaInfoPresenter(Context context, DramaInfoView callback) {
        dramaModel = new DramaModel(context);
        this.callback = callback;
    }

    public void fetchDramaList(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<DramaData> dramaList = dramaModel.fetchDramaList();
                        callback.updateView(dramaList);
                    }
                }
        ).start();
    }

    public void destroy (){
        callback = null;
        if (dramaModel != null){
            dramaModel.destroy();
        }
        dramaModel = null;
    }
}
