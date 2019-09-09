package idv.jerry.dramalist.activity;

import java.util.ArrayList;

import idv.jerry.dramalist.data.DramaData;

public interface MainView {

    void updateDramaRecyclerView(ArrayList<DramaData> dramaList);
}
