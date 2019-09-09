package idv.jerry.dramalist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import idv.jerry.dramalist.R;
import idv.jerry.dramalist.config.AppConfig;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.data.ResponseData;
import idv.jerry.dramalist.presenter.DramaInfoPresenter;
import idv.jerry.dramalist.utils.GlideUtils;
import idv.jerry.dramalist.utils.GsonUtils;
import idv.jerry.dramalist.utils.SharedPreferencesUtil;

public class DramaInfoActivity extends Activity implements DramaInfoView {

    public static final String BUNDLE_KEY = "DramaKey";
    private DramaInfoPresenter dramaInfoPresenter;
    private ImageView ivThumb;
    private TextView tvDramaName, tvRating, tvViewCount, tvPublishDate;
    private String dramaId = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drama_info);

        DramaData dramaData = null;
        Intent intent = getIntent();
        if (intent != null){

            String data = intent.getDataString();

            if (data == null){
                Bundle bundle = intent.getExtras();
                if (bundle != null){
                    dramaData = bundle.getParcelable(BUNDLE_KEY);
                }
            } else {

                dramaId = data.substring(data.lastIndexOf("/:") + 2);
                String json = SharedPreferencesUtil.getString(DramaInfoActivity.this, AppConfig.dataKey);
                ResponseData<DramaData> responseData = GsonUtils.parseJson(json);

                try {
                    for (int i = 0; i < responseData.data.size(); i++){
                        DramaData tmpData = responseData.data.get(i);
                        if (tmpData.drama_id == Integer.parseInt(dramaId) ){
                            dramaData = tmpData;
                            break;
                        }
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }

        }

        dramaInfoPresenter = new DramaInfoPresenter(this,this);


        ivThumb = findViewById(R.id.iv_thumb);
        tvDramaName = findViewById(R.id.tv_drama_name);
        tvRating = findViewById(R.id.tv_publish_date);
        tvViewCount = findViewById(R.id.tv_view_count);
        tvPublishDate = findViewById(R.id.tv_publish_date);

        if (dramaData != null){
            setupView(dramaData);
        } else {
            //  沒資料，call api 抓列表回來。然後updateView更新view
            dramaInfoPresenter.fetchDramaList();
        }
    }

    private void setupView(DramaData dramaData){
        if (dramaData != null){
            GlideUtils.loadImages(dramaData.thumb, ivThumb);

            tvDramaName.setText(dramaData.name);
            tvRating.setText(String.valueOf(dramaData.rating));
            tvViewCount.setText(String.valueOf(dramaData.total_views));
            tvPublishDate.setText(dramaData.created_at);
        }
    }

    @Override
    public void updateView(final ArrayList<DramaData> aList){
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (dramaId != null){
                            DramaData dramaData = null;
                            try {
                                for (int i = 0; i < aList.size(); i++){
                                    DramaData data = aList.get(i);
                                    if (data.drama_id == Integer.parseInt(dramaId)){
                                        dramaData = data;
                                        break;
                                    }
                                }
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                            }

                            setupView(dramaData);

                        }
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dramaInfoPresenter != null){
            dramaInfoPresenter.destroy();
        }
    }
}
