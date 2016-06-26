package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.util.ImageLoadUtils;
import com.tatait.tataweibo.util.show.CircularImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登陆界面
 *
 * @author WSXL
 */
public class LoginCircleActivity extends Activity {
    @Bind(R.id.cover_user_photo)
    CircularImage coverUserPhoto;
    @Bind(R.id.circle_text)
    TextView circleText;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_circle);
        ButterKnife.bind(this);
        ImageLoadUtils.initImageLoads(getApplicationContext());
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.bgpic)
//                .showImageOnFail(R.drawable.bgpic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        UserDao dao = new UserDao(this);
        final List<UserInfo> userData = dao.findAllUser();
        if (userData.isEmpty()) {//如果用户列表为空，则跳转授权页面
            Intent intent = new Intent(LoginCircleActivity.this, OAuthActivity.class);
            startActivity(intent);
            finish();
        } else {
            //否则加载欢迎动画
            AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(1000);
            circleText.setAnimation(animation);
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    UserSession.nowUser = userData.get(0);
                    imageLoader.displayImage(userData.get(0).getUser_head(), coverUserPhoto, options);
//                    coverUserPhoto.setImageDrawable(userData.get(0).getUser_head());
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity(new Intent(LoginCircleActivity.this, TabMainActivity.class));
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }
}
