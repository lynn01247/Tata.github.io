package com.tatait.tataweibo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tatait.tataweibo.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("SimpleDateFormat")
public class AboutUsActivity extends Activity implements
        OnClickListener {
    @Bind(R.id.aboutus_layout_back)
    LinearLayout aboutus_layout_back;
    @Bind(R.id.aboutus_layout_rl)
    RelativeLayout aboutus_layout_rl;
    @Bind(R.id.aboutus_title)
    TextView aboutus_title;
    @Bind(R.id.aboutus_layout_version)
    TextView aboutus_layout_version;
    private Activity mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        aboutus_layout_back.setOnClickListener(this);
        aboutus_title.setText("关于我们");
        aboutus_layout_version.setText(getResources().getString(R.string.app_name) + " Version：" + CommonUtil.getVersion(mContext));
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            //返回
            case R.id.aboutus_layout_back:
                finish();
                break;
        }
    }
}