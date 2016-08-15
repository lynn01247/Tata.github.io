package com.tatait.tataweibo;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.FirstEvent;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.show.SlidingMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabMainActivity extends TabActivity {
    public View msgTitle;// 信息头部按钮
    CharSequence[] items = Constants.beautiful_items;
    @Bind(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @Bind(android.R.id.tabs)
    TabWidget tabs;
    @Bind(R.id.radio_button0)
    RadioButton radioButton0;
    @Bind(R.id.radio_button2)
    RadioButton radioButton2;
    @Bind(R.id.radio_button3)
    RadioButton radioButton3;
    @Bind(R.id.radio_button4)
    RadioButton radioButton4;
    @Bind(R.id.main_radio)
    RadioGroup mainRadio;
    @Bind(android.R.id.tabhost)
    TabHost tabhost;
    @Bind(R.id.id_menu)
    SlidingMenu idMenu;

    public static ImageButton menuPlayButton, menuNextButton;
    public String color = "#000000";
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.maintabs);
        //注册EventBus
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        menuPlayButton = (ImageButton) findViewById(R.id.menu_play_button);
        menuNextButton = (ImageButton) findViewById(R.id.menu_next_button);
        menuPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MusicPlayActivity.mAdapter != null) {
                    Intent play = new Intent(TabMainActivity.this,
                            MusicService.class);
                    play.putExtra("control", "play");
                    startService(play);
                } else {
                    mainRadio.check(R.id.radio_button2);
                    tabhost.setCurrentTabByTag("TAB_MUSIC");
                    Toast.makeText(TabMainActivity.this, R.string.music_init, Toast.LENGTH_SHORT).show();
                    Intent play = new Intent(TabMainActivity.this,
                            MusicService.class);
                    play.putExtra("control", "play");
                    startService(play);
                }
            }
        });
        menuNextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MusicPlayActivity.mAdapter != null) {
                    Intent next = new Intent(TabMainActivity.this,
                            MusicService.class);
                    next.putExtra("control", "next");
                    startService(next);
                } else {
                    mainRadio.check(R.id.radio_button2);
                    tabhost.setCurrentTabByTag("TAB_MUSIC");
                    Toast.makeText(TabMainActivity.this, R.string.music_init, Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(TabMainActivity.this,
                            MusicService.class);
                    next.putExtra("control", "next");
                    startService(next);
                }
            }
        });
        // 完成各子页集成
        tabhost = this.getTabHost();
        tabhost.addTab(tabhost.newTabSpec("TAB_HOME").setIndicator("TAB_HOME")
                .setContent(new Intent(this, HomeActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("TAB_MUSIC").setIndicator("TAB_MUSIC")
                .setContent(new Intent(this, MusicPlayActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("TAB_LOVE_READ").setIndicator("TAB_LOVE_READ")
                .setContent(new Intent(this, ReadActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("TAB_MORE").setIndicator("TAB_MORE")
                .setContent(new Intent(this, MoreActivity.class)));
        mainRadio.check(R.id.radio_button0);
        radioButton0.setTextColor(Color.parseColor(color));
        mainRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int rid) {
                switch (rid) {
                    case R.id.radio_button0:// 首页
                        tabhost.setCurrentTabByTag("TAB_HOME");
                        radioButton0.setTextColor(Color.parseColor(color));
                        radioButton2.setTextColor(Color.parseColor("#686767"));
                        radioButton3.setTextColor(Color.parseColor("#686767"));
                        radioButton4.setTextColor(Color.parseColor("#686767"));
                        break;
                    case R.id.radio_button2:// 音乐
                        tabhost.setCurrentTabByTag("TAB_MUSIC");
                        radioButton0.setTextColor(Color.parseColor("#686767"));
                        radioButton2.setTextColor(Color.parseColor(color));
                        radioButton3.setTextColor(Color.parseColor("#686767"));
                        radioButton4.setTextColor(Color.parseColor("#686767"));
                        break;
                    case R.id.radio_button3:// 阅读
                        tabhost.setCurrentTabByTag("TAB_LOVE_READ");
                        radioButton0.setTextColor(Color.parseColor("#686767"));
                        radioButton2.setTextColor(Color.parseColor("#686767"));
                        radioButton3.setTextColor(Color.parseColor(color));
                        radioButton4.setTextColor(Color.parseColor("#686767"));
                        break;
                    case R.id.radio_button4:// 更多
                        tabhost.setCurrentTabByTag("TAB_MORE");
                        radioButton0.setTextColor(Color.parseColor("#686767"));
                        radioButton2.setTextColor(Color.parseColor("#686767"));
                        radioButton3.setTextColor(Color.parseColor("#686767"));
                        radioButton4.setTextColor(Color.parseColor(color));
                }
            }

        });
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if ("true".equals(event.getMsg())) {
            mainRadio.setBackgroundColor(getResources().getColor(R.color.left_itembg_pressed));
        } else {
            mainRadio.setBackgroundColor(getResources().getColor(R.color.layout_bg));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    public void toggleMenu(View view) {
        idMenu.toggle();
    }
}
