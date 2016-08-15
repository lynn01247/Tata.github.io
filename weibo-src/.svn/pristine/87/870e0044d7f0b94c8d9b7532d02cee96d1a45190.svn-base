package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;
import com.tatait.tataweibo.share_auth.AuthActivity;
import com.tatait.tataweibo.share_auth.UserinfoActivity;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.AlertDialog;
import com.tatait.tataweibo.util.CommonUtil;
import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.StringUtils;
import com.tatait.tataweibo.util.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录账号管理
 */
public class LoginTypeActivity extends Activity implements OnClickListener {

    @Bind(R.id.logintype_layout_back)
    LinearLayout logintypeLayoutBack;
    @Bind(R.id.logintypes_title)
    TextView logintypesTitle;
    @Bind(R.id.logintype_layout_rl)
    RelativeLayout logintypeLayoutRl;
    @Bind(R.id.logintype_wx_state)
    TextView logintypeWxState;
    @Bind(R.id.logintype_qq_state)
    TextView logintypeQqState;
    @Bind(R.id.logintype_yk_state)
    TextView logintype_yk_state;
    @Bind(R.id.logintype_wx_logoff)
    TextView logintype_wx_logoff;
    @Bind(R.id.logintype_qq_logoff)
    TextView logintype_qq_logoff;
    @Bind(R.id.logintype_wb_state)
    TextView logintype_wb_state;
    @Bind(R.id.logintype_wb_logoff)
    TextView logintype_wb_logoff;

    private Context mAppContext;
    private Context mContext;
    private Activity mActivity;
    private UMShareAPI mShareAPI = null;
    private String screen_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        ButterKnife.bind(this);
        mAppContext = getApplicationContext();
        mContext = this;
        mActivity = this;
        initData();
    }

    private void initData() {
        mShareAPI = UMShareAPI.get(this);
        String loginType = (String) SharedPreferencesUtils.getParam(mAppContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL);
        screen_name = SharedPreferencesUtils.getParam(mAppContext,Global.WQLOGINID,"").toString();
        //校验登录状态
        UserDao dao = new UserDao(LoginTypeActivity.this);
        List<UserInfo> userList = dao.findAllUser();
        if (userList == null || userList.isEmpty()) {
            if (Global.LOGIN_TYPE_WX.equals(loginType)) {
                if (StringUtils.isEmpty(screen_name)) {
                    Global.handlerForLogoff = mhandlerForLogoff;
                    mShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                } else {
                    logintypeWxState.setText("登录账号：" + screen_name);
                }
                logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext, R.color.black_overlay));
                logintype_wx_logoff.setVisibility(View.VISIBLE);

                logintypeQqState.setText("(未使用QQ账号登录)");
                logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_qq_logoff.setVisibility(View.GONE);

                logintype_wb_state.setText("(未使用新浪微博账号登录)");
                logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wb_logoff.setVisibility(View.GONE);

                logintype_yk_state.setVisibility(View.GONE);
            } else if (Global.LOGIN_TYPE_QQ.equals(loginType)) {
                if (StringUtils.isEmpty(screen_name)) {
                    Global.handlerForLogoff = mhandlerForLogoff;
                    mShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.QQ, umAuthListener);
                } else {
                    logintypeQqState.setText("登录账号：" + screen_name);
                }
                logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext, R.color.black_overlay));
                logintype_qq_logoff.setVisibility(View.VISIBLE);

                logintypeWxState.setText("(未使用QQ账号登录)");
                logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wx_logoff.setVisibility(View.GONE);

                logintype_wb_state.setText("(未使用新浪微博账号登录)");
                logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wb_logoff.setVisibility(View.GONE);

                logintype_yk_state.setVisibility(View.GONE);
            } else if (Global.LOGIN_TYPE_YK.equals(loginType)) {
                logintypeWxState.setText("(未使用QQ账号登录)");
                logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wx_logoff.setVisibility(View.GONE);

                logintypeQqState.setText("(未使用QQ账号登录)");
                logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_qq_logoff.setVisibility(View.GONE);

                logintype_wb_state.setText("(未使用新浪微博账号登录)");
                logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wb_logoff.setVisibility(View.GONE);

                logintype_yk_state.setVisibility(View.VISIBLE);
                SpannableStringBuilder builder = new SpannableStringBuilder(logintype_yk_state.getText().toString());
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffc600")), 10, 14, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                logintype_yk_state.setText(builder);
            } else if (Global.LOGIN_TYPE_TEL.equals(loginType)) {
                logintypeWxState.setText("(未使用QQ账号登录)");
                logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wx_logoff.setVisibility(View.GONE);

                logintypeQqState.setText("(未使用QQ账号登录)");
                logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_qq_logoff.setVisibility(View.GONE);

                logintype_wb_state.setText("(未使用新浪微博账号登录)");
                logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                logintype_wb_logoff.setVisibility(View.GONE);

                logintype_yk_state.setVisibility(View.VISIBLE);
                String smsuid = (String) SharedPreferencesUtils.getParam(mAppContext,Global.SMSUID,"");
                if(smsuid == null ) smsuid = "";
                SpannableStringBuilder builder = new SpannableStringBuilder("温馨提示：您使用短信登录：" + smsuid);
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffc600")), 13, 13 + smsuid.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                logintype_yk_state.setText(builder);
            } else {
                Toast.makeText(mContext, "已注销登录，请重新登录", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            UserInfo user = MainActivity.UserSession.nowUser;
            logintype_wb_state.setText("登录账号：" + user.getUser_name());
            logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
            logintype_wb_logoff.setVisibility(View.VISIBLE);

            logintypeWxState.setText("(未使用QQ账号登录)");
            logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
            logintype_wx_logoff.setVisibility(View.GONE);

            logintypeQqState.setText("(未使用QQ账号登录)");
            logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
            logintype_qq_logoff.setVisibility(View.GONE);

            logintype_yk_state.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.logintype_layout_back,R.id.logintype_yk_state,R.id.logintype_wx_logoff,R.id.logintype_qq_logoff,R.id.logintype_wb_logoff})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logintype_layout_back:
                finish();
                break;
            case R.id.logintype_wb_logoff:
                new AlertDialog(mContext).builder().setTitle("温馨提示").setMsg("确定要注销登录？").setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).setPositiveButton("注销", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtils.setParam(mAppContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL);
                        SharedPreferencesUtils.setParam(mAppContext, Global.WQLOGINID, "");

                        logintype_wb_state.setText("(未使用新浪微博账号登录)");
                        logintype_wb_state.setTextColor(ContextCompat.getColor(mAppContext, R.color.gray_dark));
                        logintype_wb_state.setVisibility(View.GONE);

                        AccessTokenKeeper.clear(getApplicationContext());
                        Toast.makeText(mAppContext, "已注销", Toast.LENGTH_LONG).show();
                        UserDao dao = new UserDao(mAppContext);
                        dao.deleteAllUser();
                        Intent intent = new Intent(LoginTypeActivity.this, OAuthActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
                break;
            case R.id.logintype_yk_state:
                logintype_yk_state.setClickable(false);
                String smsuid = (String) SharedPreferencesUtils.getParam(mAppContext,Global.SMSUID,"");
                String type = "即将清除游客身份状态？";
                if(!"".equals(smsuid)){
                    type = "即将清除手机验证状态？";
                }
                new AlertDialog(mContext).builder().setTitle("温馨提示").setMsg(type).setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logintype_yk_state.setClickable(true);
                    }}).setPositiveButton("清除", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtils.setParam(mAppContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL);
                        SharedPreferencesUtils.setParam(mAppContext, Global.WQLOGINID, "");
                        SharedPreferencesUtils.setParam(mAppContext, Global.SMSUID, "");
                        logintype_yk_state.setVisibility(View.GONE);
                        logintype_yk_state.setClickable(true);
                    }
                }).show();
                break;
            case R.id.logintype_wx_logoff:
                new AlertDialog(mContext).builder().setTitle("温馨提示").setMsg("确定要注销登录？").setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).setPositiveButton("注销", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Global.handlerForLogoff = mhandlerForLogoff;
                        SharedPreferencesUtils.setParam(mAppContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL);
                        SharedPreferencesUtils.setParam(mAppContext, Global.WQLOGINID, "");
                        mShareAPI.deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, umdelAuthListener);
                    }
                }).show();
                break;
            case R.id.logintype_qq_logoff:
                new AlertDialog(mContext).builder().setTitle("温馨提示").setMsg("确定要注销登录？").setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {}}).setPositiveButton("注销", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtils.setParam(mAppContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL);
                        SharedPreferencesUtils.setParam(mAppContext, Global.WQLOGINID, "");
                        mShareAPI.deleteOauth(mActivity, SHARE_MEDIA.QQ, umdelAuthListener);
                    }
                }).show();
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
//                Log.d("auth callbacl", "getting data");
//                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                String type = "";
                if(data.size()>0){
                    if(platform.equals(SHARE_MEDIA.QQ)) {
                        type = "QQ";
                        screen_name = data.get("screen_name");
                    }else if(platform.equals(SHARE_MEDIA.WEIXIN)){
                        type = "WEIXIN";
                        screen_name = data.get("nickname");
                    }
                    SharedPreferencesUtils.setParam(mAppContext, Global.WQLOGINID, screen_name);
                    Handler handlerForLogoff = Global.handlerForLogoff;
                    if (handlerForLogoff != null) {
                        Message message = new Message();
                        message.what = 998;
                        Bundle bundle = new Bundle();
                        bundle.putString("platform",type);
                        bundle.putString("screen_name",screen_name);
                        message.setData(bundle);
                        handlerForLogoff.sendMessage(message);
                    }
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext, "注销成功", Toast.LENGTH_SHORT).show();
            Handler handlerForLogoff = Global.handlerForLogoff;
            if (handlerForLogoff != null) {
                Message message = new Message();
                message.what = 996;
                Bundle bundle = new Bundle();
                message.setData(bundle);
                handlerForLogoff.sendMessage(message);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(mContext, "注销失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "操作取消", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 子线程回调
     */
    public Handler mhandlerForLogoff = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 998://注销回调
                    if(bundle != null && "QQ".equals(bundle.getString("platform"))){
                        logintypeQqState.setText("登录账号：" + screen_name);
                    }else{
                        logintypeWxState.setText("登录账号：" + screen_name);
                    }
                    break;
                case 996:
                    logintypeWxState.setText("(未使用QQ账号登录)");
                    logintypeWxState.setTextColor(ContextCompat.getColor(mAppContext,R.color.gray_dark));
                    logintype_wx_logoff.setVisibility(View.GONE);

                    logintypeQqState.setText("(未使用QQ账号登录)");
                    logintypeQqState.setTextColor(ContextCompat.getColor(mAppContext,R.color.gray_dark));
                    logintype_qq_logoff.setVisibility(View.GONE);
                    break;
            }
        }
    };
}