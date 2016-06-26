package com.tatait.tataweibo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInfoActivity extends Activity {
    @Bind(R.id.status_return_weibo)
    ImageButton statusReturnWeibo;
    @Bind(R.id.txt_userName)
    TextView txtUserName;
    @Bind(R.id.userinfo_image)
    ImageView userinfo_image;
    @Bind(R.id.userinfo_username)
    TextView userinfo_username;
    @Bind(R.id.userinfo_icon)
    TextView userinfo_icon;
    @Bind(R.id.userinfo_address)
    TextView userinfo_address;
    @Bind(R.id.userinfo_loginName)
    TextView userinfo_loginName;
    @Bind(R.id.userinfo_friendsCount)
    TextView userinfo_friendsCount;
    @Bind(R.id.userinfo_statusesCount)
    TextView userinfo_statusesCount;
    @Bind(R.id.userinfo_followingCount)
    TextView userinfo_followingCount;
    @Bind(R.id.userinfo_favourites_count)
    TextView userinfo_favourites_count;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化数据操作
     */
    private void init() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.bgpic)
//                .showImageOnFail(R.drawable.bgpic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        UserInfo user = UserSession.nowUser;

        /** 给控件赋值 */
        imageLoader.displayImage(user.getUser_head(), userinfo_image, options);
        if ("m".equalsIgnoreCase(user.getGender())) {
            userinfo_icon.setText("女");
        } else if ("f".equalsIgnoreCase(user.getGender())) {
            userinfo_icon.setText("男");
        } else {
            userinfo_icon.setText("");
        }
        userinfo_username.setText(user.getScreen_name());
        userinfo_address.setText("地址：" + user.getLocation());
        userinfo_loginName.setText("登录名：" + user.getScreen_name());
        userinfo_friendsCount.setText("关注数：" + user.getFriends_count());
        userinfo_followingCount.setText("粉丝数：" + user.getFollowers_count());
        userinfo_statusesCount.setText("微博数：" + user.getStatuses_count());
        userinfo_favourites_count.setText("收藏数：" + user.getFavourites_count());

    }

}
