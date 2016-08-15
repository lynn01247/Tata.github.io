package com.tatait.tataweibo.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tatait.tataweibo.OAuthActivity;
import com.tatait.tataweibo.TabMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * 短信验证码类
 *
 * @author lynn
 */
public class SMSUtils {

    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    public static void init(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = mActivity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = mActivity.checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readSms = mActivity.checkSelfPermission(Manifest.permission.READ_SMS);
            int readContacts = mActivity.checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int readSdcard = mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<String>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_SMS);
            }
            if (readContacts != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 4;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                mActivity.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
        initSDK(mActivity);
    }

    private static void initSDK(final Activity mActivity) {
        // 初始化短信SDK
        SMSSDK.initSDK(mActivity, Global.SMSAPPKEY, Global.SMSAPPSECRET);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    // 提交用户信息
                    registerUser(country, phone,mActivity);
                }
            }
        });
        registerPage.show(mActivity);


    }

    public static void destroy() {
        SMSSDK.unregisterAllEventHandler();
    }

    // 提交用户信息
    private static void registerUser(String country, String phone, Activity mActivity) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
        SharedPreferencesUtils.setParam(mActivity, Global.SMSUID, nickName + "_" + phone );
        SharedPreferencesUtils.setParam(mActivity, Global.LOGIN_TYPE, Global.LOGIN_TYPE_TEL);
        Handler handlerForSMSLogin = Global.handlerForSMSLogin;
        if (handlerForSMSLogin != null) {
            Message message = new Message();
            message.what = 666;
            Bundle bundle = new Bundle();
            message.setData(bundle);
            handlerForSMSLogin.sendMessage(message);
        }
    }
}
