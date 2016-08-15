package com.tatait.tataweibo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class FeekBackActivity extends Activity implements OnClickListener {


    @Bind(R.id.feedback_layout_back)
    LinearLayout feedbackLayoutBack;
    @Bind(R.id.feedbacks_title)
    TextView feedbacksTitle;
    @Bind(R.id.feedback_layout_rl)
    RelativeLayout feedbackLayoutRl;
    @Bind(R.id.feekback_activity_phone_edi)
    EditText feekbackActivityPhoneEdi;
    @Bind(R.id.feekback_activity_content_edi)
    EditText feekbackActivityContentEdi;
    @Bind(R.id.feekback_activity_submit)
    Button feekbackActivitySubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feek_back);
        ButterKnife.bind(this);
//        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.feedback_layout_back, R.id.feekback_activity_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_layout_back:
                finish();
                break;
            case R.id.feekback_activity_submit:
                Toast.makeText(this,"抱歉，当前服务器正处于维护阶段，暂时无法提交",Toast.LENGTH_LONG).show();
                break;
        }
    }
}