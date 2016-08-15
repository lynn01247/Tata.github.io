package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetFrontActivity extends Activity implements
        OnClickListener {

    @Bind(R.id.setfront_layout_back)
    LinearLayout setfront_layout_back;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;

    private Context mContext;
    private Activity mActivity;
    private int mprogress = 20;
    private String fonts;
    private final String mPageName = "SetFrontActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setfront);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        initData();
    }

    private void initData() {
        setfront_layout_back.setOnClickListener(this);
        fonts = StringUtils.getObjStringWithDef(SharedPreferencesUtils.getParam(mContext, "fontscale", "1.0"), "1.0");
        if ("1.4".equals(fonts)) {
            seekBar.setProgress(60);
            tv1.setText("特大号");
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
        } else if ("1.2".equals(fonts)) {
            seekBar.setProgress(40);
            tv1.setText("大号");
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 33);
        } else if ("0.8".equals(fonts)) {
            seekBar.setProgress(0);
            tv1.setText("小号");
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 23);
        } else {
            seekBar.setProgress(20);
            tv1.setText("默认");
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mprogress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Float progressdou = ((float) mprogress);

                if (progressdou > 50) {
                    seekBar.setProgress(60);
                    tv1.setText("特大号");
                    fonts = "1.4";
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
                } else if (progressdou > 30 && progressdou <= 50) {
                    seekBar.setProgress(40);
                    tv1.setText("大号");
                    fonts = "1.2";
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 33);
                } else if (progressdou > 10 && progressdou <= 30) {
                    seekBar.setProgress(20);
                    tv1.setText("默认");
                    fonts = "1.0";
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28);
                } else if (progressdou <= 10) {
                    seekBar.setProgress(0);
                    tv1.setText("小号");
                    fonts = "0.8";
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 23);
                }

                SharedPreferencesUtils.setParam(mContext, "fontscale", StringUtils.getObjString(fonts));
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            //返回
            case R.id.setfront_layout_back:
                finish();
                break;
        }
    }

}