package com.tatait.tataweibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.Tools;
import com.umeng.socialize.Config;

import java.util.List;

/**
 * 加载logo页面
 *
 * @author WSXL
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        Config.dialogSwitch=true;
//          如想让你的app在android 6.0系统上也能运行的话，需要动态获取权限，没有权限的话分享sdk会出错，参考一下代码做动态获取权限,适配安卓6.0系统
//          你需要最新的android.support.v4包，或者v13的包可也以
//            if(Build.VERSION.SDK_INT>=23){
//                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
//                ActivityCompat.requestPermissions(this,mPermissionList,REQUEST_PERM);
//            }
        //分享跳转
        // Intent intent = new Intent(MainActivity.this, ShareandAuthActivity.class);
        //         startActivity(intent);
        if ((Boolean) SharedPreferencesUtils.getParam(getApplicationContext(), Global.LOAD, false)) {
            ImageView loadImage = (ImageView) findViewById(R.id.mainImage);
            //设置透明度渐变效果(0.0-1.0)
            AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
            //设置动画持续时间
            animation.setDuration(2000);
            //将组件与动画进行关联
            loadImage.setAnimation(animation);
            animation.setAnimationListener(new AnimationListener() {
                //动画开始
                @Override
                public void onAnimationStart(Animation animation) {
                    Toast.makeText(MainActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
                }

                //动画结束
                @Override
                public void onAnimationEnd(Animation animation) {
                    init();
                }

                //动画重复
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
            init();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.load, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化动作
     */
    public void init() {
        //校验网络状态
        if (!Tools.checkNetwork(MainActivity.this)) {
            TextView netSetting = new TextView(MainActivity.this);
            netSetting.setText("当前没有可用的网络连接，请设置网络");
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.sad)
                    .setTitle("网络状态提示")
                    .setView(netSetting)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                                }
                            }).create().show();
        } else {
            //校验登录状态
            UserDao dao = new UserDao(MainActivity.this);
            List<UserInfo> userList = dao.findAllUser();
            if (userList == null || userList.isEmpty()) {
                //判断--》没有登录？--》是否是游客登录状态、微信QQ登录状态
                if(!Global.LOGIN_TYPE_NULL.equals(SharedPreferencesUtils.getParam(getApplicationContext(), Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL))){
                    startActivity(new Intent(this, TabMainActivity.class));
                    finish();
                }else {
                    //如果没有登录且非游客登录则跳转到登录授权页面
                    Intent intent = new Intent(this, OAuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                //如果登录过则跳转到登录页面
                Intent intent = new Intent(this, LoginCircleActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public static class UserSession {
        public static UserInfo nowUser;
    }
}