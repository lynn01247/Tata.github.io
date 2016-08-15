package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.FirstEvent;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.SwitchView_tip;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.show.CircularImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreActivity extends Activity {
    @Bind(R.id.layout_title_bar)
    FrameLayout layout_title_bar;
    @Bind(R.id.home_title_bar_user_photo)
    CircularImage circularImage;
    @Bind(R.id.home_title_login_user)
    TextView txt_wb_title;
    @Bind(R.id.menu_btn_right)
    Button menu_btn_right;
    @Bind(R.id.main_myslipswitch)
    SwitchView_tip slipswitch_MSL;
    @Bind(R.id.night_myslipswitch)
    SwitchView_tip night_myslipswitch;
    @Bind(R.id.more_ll)
    LinearLayout more_ll;
    @Bind(R.id.more_login_type)
    RelativeLayout more_login_type;
    @Bind(R.id.more_app_content)
    RelativeLayout more_app_content;
    @Bind(R.id.more_feedback)
    RelativeLayout more_feedback;
    @Bind(R.id.more_device_info)
    RelativeLayout more_device_info;

    CharSequence[] items = Constants.beautiful_items;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);
        ButterKnife.bind(this);
        initView();
        inisetstyle();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    private void inisetstyle() {
        boolean night = (Boolean) SharedPreferencesUtils.getParam(getApplicationContext(), Global.NIGHT, false);
        if (night) {
            layout_title_bar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.left_itembg_pressed));
            txt_wb_title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
            more_ll.setBackgroundResource(R.drawable.shape_black_white);
        } else {
            layout_title_bar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_press));
            txt_wb_title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            more_ll.setBackgroundResource(R.drawable.shape_blue_white);
        }
    }

    private void initView() {
        txt_wb_title.setText(R.string.setting_text);
        circularImage.setVisibility(View.GONE);
        menu_btn_right.setVisibility(View.GONE);

        slipswitch_MSL.setOpened((Boolean) SharedPreferencesUtils.getParam(getApplicationContext(), Global.LOAD, false));
        slipswitch_MSL.setOnSwitchListener(new SwitchView_tip.OnSwitchListener() {
            @Override
            public void onSwitched(boolean isSwitchOn) {
                if (isSwitchOn) {
                    SharedPreferencesUtils.setParam(MoreActivity.this, Global.LOAD, true);
                } else {
                    SharedPreferencesUtils.setParam(MoreActivity.this, Global.LOAD, false);
                }
            }
        });
//        slipswitch_MSL.setOnStateChangedListener(new SwitchView.OnStateChangedListener(){
//            @Override
//            public void toggleToOn(View view) {
//                // 原本为关闭的状态，被点击后
//                SharedPreferencesUtils.setParam(MoreActivity.this, Global.LOAD, true);
////                // 执行一些耗时的业务逻辑操作 implement some time-consuming logic operation
////                slipswitch_MSL.postDelayed(new Runnable() {
////                    @Override public void run() {
////                        slipswitch_MSL.toggleSwitch(true); //以动画效果切换到打开的状态
////                    }, 1000);
////                }
//            }
//            @Override
//            public void toggleToOff(View view) {
//                // 原本为打开的状态，被点击后
//                SharedPreferencesUtils.setParam(MoreActivity.this, Global.LOAD, false);
//            }
//        });
        night_myslipswitch.setOpened((Boolean) SharedPreferencesUtils.getParam(getApplicationContext(), Global.NIGHT, false));
        night_myslipswitch.setOnSwitchListener(new SwitchView_tip.OnSwitchListener() {
            @Override
            public void onSwitched(boolean isSwitchOn) {
                if (isSwitchOn) {
                    SharedPreferencesUtils.setParam(MoreActivity.this, Global.NIGHT, true);
                    EventBus.getDefault().post(new FirstEvent("true"));
                } else {
                    SharedPreferencesUtils.setParam(MoreActivity.this, Global.NIGHT, false);
                    EventBus.getDefault().post(new FirstEvent("false"));
                }
            }
        });
//        night_myslipswitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener(){
//            @Override
//            public void toggleToOn(View view) {
//                // 原本为关闭的状态，被点击后
//                SharedPreferencesUtils.setParam(MoreActivity.this, Global.NIGHT, true);
//                EventBus.getDefault().post(new FirstEvent("true"));
//            }
//            @Override
//            public void toggleToOff(View view) {
//                // 原本为打开的状态，被点击后
//                SharedPreferencesUtils.setParam(MoreActivity.this, Global.NIGHT, false);
//                EventBus.getDefault().post(new FirstEvent("false"));
//            }
//        });
        //关于
        //账号管理
        more_login_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this,LoginTypeActivity.class));
            }
        });
        //关于
        more_app_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this,AboutUsActivity.class));
            }
        });
        //意见反馈
        more_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this,FeekBackActivity.class));
            }
        });
        //设备信息
        more_device_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this,DeviceInfoActivity.class));
            }
        });
    }

    /**
     * 设置键盘事件处理
     */
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.quit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if (Tools.isServiceRunning(this, "com.tatait.tataweibo.service.MusicService")) {
                    stopService(new Intent(MoreActivity.this, MusicService.class));
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if ("true".equals(event.getMsg())) {
            layout_title_bar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.left_itembg_pressed));
            txt_wb_title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
            more_ll.setBackgroundResource(R.drawable.shape_black_white);
        } else {
            layout_title_bar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_press));
            txt_wb_title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            more_ll.setBackgroundResource(R.drawable.shape_blue_white);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }
}