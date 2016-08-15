package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreBackActivity extends Activity {
    @Bind(R.id.layout_title_bar)
    FrameLayout layout_title_bar;
    @Bind(R.id.home_title_bar_user_photo)
    CircularImage circularImage;
    @Bind(R.id.home_title_login_user)
    TextView txt_wb_title;
    @Bind(R.id.menu_btn_right)
    Button menu_btn_right;
    @Bind(R.id.gv_foot_menu)
    GridView gv_foot_menu;
    @Bind(R.id.main_myslipswitch)
    SwitchView_tip slipswitch_MSL;
    @Bind(R.id.night_myslipswitch)
    SwitchView_tip night_myslipswitch;
    @Bind(R.id.more_ll)
    LinearLayout more_ll;
//    @Bind(R.id.id_menu)
//    SlidingMenu mMenu;

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
            layout_title_bar.setBackgroundColor(getResources().getColor(R.color.left_itembg_pressed));
            txt_wb_title.setTextColor(getResources().getColor(R.color.gray));
            more_ll.setBackgroundResource(R.drawable.shape_black_white);
        } else {
            layout_title_bar.setBackgroundColor(getResources().getColor(R.color.blue_press));
            txt_wb_title.setTextColor(getResources().getColor(R.color.white));
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
                if(isSwitchOn){
                    SharedPreferencesUtils.setParam(MoreBackActivity.this, Global.LOAD, true);
                }else{
                    SharedPreferencesUtils.setParam(MoreBackActivity.this, Global.LOAD, false);
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
                if(isSwitchOn){
                    SharedPreferencesUtils.setParam(MoreBackActivity.this, Global.NIGHT, true);
                    EventBus.getDefault().post(new FirstEvent("true"));
                }else{
                    SharedPreferencesUtils.setParam(MoreBackActivity.this, Global.NIGHT, false);
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
    }

    /**
     * 设置键盘事件处理
     */
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (gv_foot_menu == null) {
                loadButtomMenu();
            }
            if (gv_foot_menu.getVisibility() == View.GONE) {
                gv_foot_menu.setVisibility(View.VISIBLE);
            } else {
                gv_foot_menu.setVisibility(View.GONE);
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.quit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if (Tools.isServiceRunning(this, "com.tatait.tataweibo.service.MusicService")) {
                    stopService(new Intent(MoreBackActivity.this, MusicService.class));
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    public void toggleMenu(View view) {
//        mMenu.toggle();
//    }

    private void loadButtomMenu() {
        // 设置组件背景
        gv_foot_menu.setBackgroundResource(android.R.drawable.bottom_bar);
        gv_foot_menu.setNumColumns(6);// 总共显示多少项
        gv_foot_menu.setGravity(Gravity.CENTER);// 中父组件的中间显示
        gv_foot_menu.setVerticalSpacing(5);// 垂直间隔
        gv_foot_menu.setHorizontalSpacing(5);// 水平间隔

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();

        // 添加自定义菜单显示的图片
        map.put("itemImage", Integer.valueOf(R.drawable.usrinfo_fans_n).toString()); //
        // 添加自定义菜单显示的文字
        map.put("itemText", this.getResources().getString(R.string.menu_friends_timeline));
        data.add(map);
        map = new HashMap<String, String>();
        map.put("itemImage", Integer.valueOf(R.drawable.officialweibo).toString());
        map.put("itemText", this.getResources().getString(R.string.menu_user_timeline));
        data.add(map);
        map = new HashMap<String, String>();
        map.put("itemImage", Integer.valueOf(R.drawable.thumbs_up2).toString());
        map.put("itemText", this.getResources().getString(R.string.menu_comments_by_me));
        data.add(map);
        map = new HashMap<String, String>();
        map.put("itemImage", Integer.valueOf(R.drawable.pl).toString());
        map.put("itemText", this.getResources().getString(R.string.menu_comments_to_me));
        data.add(map);
        map = new HashMap<String, String>();
        map.put("itemImage", Integer.valueOf(R.drawable.menu_contact).toString());
        map.put("itemText", this.getResources().getString(R.string.menu_userinfo_show));
        data.add(map);
        map = new HashMap<String, String>();
        map.put("itemImage", Integer.valueOf(R.drawable.menu_exit).toString());
        map.put("itemText", this.getResources().getString(R.string.menu_end_session));
        data.add(map);

        // 使用simpleAdapter进行数据的显示设置
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.foot_item_menu,
                new String[]{"itemImage", "itemText"}, new int[]{R.id.item_image, R.id.item_text});
        // 将adapter设置到GridView组件中
        gv_foot_menu.setAdapter(adapter);
        // 设置GridView事件
        gv_foot_menu.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:// 获得我关注的数据
                        Toast.makeText(
                                MoreBackActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(
                                MoreBackActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(
                                MoreBackActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(
                                MoreBackActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(
                                MoreBackActivity.this, items[(int) (Math.random() * items.length)].toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        gv_foot_menu.setVisibility(View.GONE);
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if ("true".equals(event.getMsg())) {
            layout_title_bar.setBackgroundColor(getResources().getColor(R.color.left_itembg_pressed));
            txt_wb_title.setTextColor(getResources().getColor(R.color.gray));
            more_ll.setBackgroundResource(R.drawable.shape_black_white);
        } else {
            layout_title_bar.setBackgroundColor(getResources().getColor(R.color.blue_press));
            txt_wb_title.setTextColor(getResources().getColor(R.color.white));
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
