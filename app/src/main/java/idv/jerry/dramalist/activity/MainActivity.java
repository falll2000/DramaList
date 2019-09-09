package idv.jerry.dramalist.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import idv.jerry.dramalist.R;
import idv.jerry.dramalist.adapter.DramaAdapter;
import idv.jerry.dramalist.config.AppConfig;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.data.ResponseData;
import idv.jerry.dramalist.presenter.MainPresenter;
import idv.jerry.dramalist.utils.GsonUtils;
import idv.jerry.dramalist.utils.NetworkUtils;
import idv.jerry.dramalist.utils.SharedPreferencesUtil;

public class MainActivity extends Activity implements MainView{

    private final String TAG = getClass().getCanonicalName();

    private RecyclerView rvDramaList;
    private EditText etKeyword;
    private DramaAdapter dramaAdapter;
    private MainPresenter mainPresenter;
    private boolean isNetworkActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rvDramaList = findViewById(R.id.rv_drama_list);
        etKeyword = findViewById(R.id.et_keyword);

        mainPresenter = new MainPresenter(this, this);

        rvDramaList.setLayoutManager(new LinearLayoutManager(this));
        rvDramaList.addItemDecoration(new DividerItemDecoration(this.getResources().getDimensionPixelOffset(R.dimen.dp_2)));
        dramaAdapter = new DramaAdapter(this);
        dramaAdapter.setOnClickListener(onClickListener);
        rvDramaList.setAdapter(dramaAdapter);

        String json = SharedPreferencesUtil.getString(this, AppConfig.dataKey);

        isNetworkActive = NetworkUtils.getNetworkStatus(this);

        if (isNetworkActive){
            Log.d(TAG, "SharedPreference No Data");
            mainPresenter.fetchDramaList();
        } else {
            if (json != null && !json.isEmpty()){
                mainPresenter.getDramaListFromJson(json);
            }
        }

        String searchKeyword = SharedPreferencesUtil.getString(MainActivity.this, AppConfig.searchKeywordKey);
        if (searchKeyword != null && !searchKeyword.isEmpty()){
            etKeyword.setText(searchKeyword);
        }
        etKeyword.addTextChangedListener(new CustomTextWatcher());

    }



    @Override
    public void updateDramaRecyclerView(final ArrayList<DramaData> dramaList) {
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        String filterText = SharedPreferencesUtil.getString(MainActivity.this, AppConfig.searchKeywordKey);

                        ArrayList<DramaData> filterList = new ArrayList<>();
                        if (filterText != null && !filterText.isEmpty()){
                            for (DramaData dramaData: dramaList){
                                if (dramaData != null && dramaData.name.contains(filterText)){
                                    filterList.add(dramaData);
                                }
                            }
                        } else {
                            filterList.addAll(dramaList);
                        }

                        dramaAdapter.updateDramaList(filterList);

                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dramaAdapter != null){
            dramaAdapter.destroy();
        }

        if (mainPresenter != null){
            mainPresenter.destroy();
        }
    }

    class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchKeyword = s.toString();

            SharedPreferencesUtil.putString(MainActivity.this, AppConfig.searchKeywordKey, searchKeyword);

            ArrayList<DramaData> tmpList = mainPresenter.getDreamList();
            updateDramaRecyclerView(tmpList);
        }
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration{
        private int space = 0;

        public DividerItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent.getChildAdapterPosition(view) == 0){
                outRect.top = space;
                outRect.bottom = space / 2;
            } else if (parent.getChildAdapterPosition(view) == mainPresenter.getDreamList().size() -1 ) {
                outRect.top = space / 2;
                outRect.bottom = space;
            } else {
                outRect.top = space / 2;
                outRect.bottom = space / 2;
            }
        }
    }

    private DramaAdapter.OnClickListener onClickListener = new DramaAdapter.OnClickListener() {
        @Override
        public void onClick(int position) {

            ArrayList<DramaData> dramaList = mainPresenter.getDreamList();
            DramaData data = dramaList.get(position);

            Intent intent = new Intent(MainActivity.this, DramaInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DramaInfoActivity.BUNDLE_KEY, data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
