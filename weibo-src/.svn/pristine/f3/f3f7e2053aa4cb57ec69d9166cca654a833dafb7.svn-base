/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2014年 mob.com. All rights reserved.
 */
package com.tatait.tataweibo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMSLoginActivity extends Activity implements View.OnClickListener {

    private EditText phone, provingCode;
    private Button submit, proving;
    private RelativeLayout smslogiin_layout_rl;
    private TimeCount time;
    private boolean greenMobile;
    private static final int SUBMIT_VERIFICATION_CODE_COMPLETE = 1;
    private static final int GET_VERIFICATION_CODE_COMPLETE = 2;
    private static final int RESULT_ERROR = 3;
    private static final int SUBMIT_VERIFICATION_CODE_COMPLETE_2 = 4;//已经智能验证过了
    private EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                // 回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 提交验证码成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE_COMPLETE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功
                    boolean smart = (Boolean) data;
                    if (smart) {
                        //通过智能验证
                        handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE_COMPLETE_2);
                    } else {
                        //依然走短信验证
                        handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE_COMPLETE);
                    }
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object;
                try {
                    object = new JSONObject(throwable.getMessage());
                    Message msg = new Message();

                    Bundle bundle = new Bundle();

                    bundle.putInt("status", object.optInt("status"));// 错误代码
                    bundle.putString("detail", object.optString("detail"));// 错误描述
                    msg.setData(bundle);
                    msg.what = RESULT_ERROR;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Handler handlerForSMSLogin = Global.handlerForSMSLogin;
            switch (msg.what) {
                case SUBMIT_VERIFICATION_CODE_COMPLETE:
                    Toast.makeText(SMSLoginActivity.this, "获取成功", Toast.LENGTH_LONG).show();
                    break;
                case SUBMIT_VERIFICATION_CODE_COMPLETE_2:
                    Toast.makeText(SMSLoginActivity.this, "当前手机已认证过，无需再获取验证码", Toast.LENGTH_LONG).show();
                    submit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    if (time != null) {
                        time.cancel();
                        submit.setText("当前手机已认证过");
                        submit.setClickable(false);
                    }
                    greenMobile = true;
                    proving.setText("认证完成，直接登录");
                    phone.setEnabled(false);
                    provingCode.setEnabled(false);
                    break;
                case GET_VERIFICATION_CODE_COMPLETE:
                    Toast.makeText(SMSLoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.setParam(SMSLoginActivity.this, Global.SMSUID, phone.getText().toString().trim());
                    SharedPreferencesUtils.setParam(SMSLoginActivity.this, Global.LOGIN_TYPE, Global.LOGIN_TYPE_TEL);
                    if (handlerForSMSLogin != null) {
                        Message message = new Message();
                        message.what = 666;
                        Bundle bundle = new Bundle();
                        message.setData(bundle);
                        handlerForSMSLogin.sendMessage(message);
                    }
                    break;
                case RESULT_ERROR:
                    Toast.makeText(
                            SMSLoginActivity.this,
                            msg.getData().getInt("status")
                                    + msg.getData().getString("detail"),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smslogin_activity);
        // 初始化短信SDK
        SMSSDK.initSDK(SMSLoginActivity.this, Global.SMSAPPKEY, Global.SMSAPPSECRET);
        initView();
        greenMobile = false;
        time = new TimeCount(60000, 1000);
    }

    private void initView() {
        smslogiin_layout_rl = (RelativeLayout) findViewById(R.id.smslogiin_layout_rl);
        phone = (EditText) findViewById(R.id.edit_phone);
        submit = (Button) findViewById(R.id.btn_submit);
        provingCode = (EditText) findViewById(R.id.edit_proving);
        proving = (Button) findViewById(R.id.btn_proving);
        proving.setOnClickListener(this);
        submit.setOnClickListener(this);
        smslogiin_layout_rl.setOnClickListener(this);
        //注册回调
        SMSSDK.registerEventHandler(eh);
    }

    /**
     * 60秒计时器
     */
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            submit.setText("获取验证码");
            submit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            submit.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            submit.setClickable(false);//防止重复点击
            submit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray_color));
            submit.setText("重新获取( " + millisUntilFinished / 1000 + "s ）");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                //获取验证
                if (StringUtils.isEmpty(phone.getText().toString().trim())) {
                    Toast.makeText(SMSLoginActivity.this, "请填写11位手机号码.", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.getVerificationCode("86", phone.getText().toString().trim());
                    // SMSSDK.getSupportedCountries();
                    time.start();// 开始计时
                }
                break;
            case R.id.btn_proving:
                if (greenMobile) {
                    SharedPreferencesUtils.setParam(SMSLoginActivity.this, Global.SMSUID, phone.getText().toString().trim());
                    SharedPreferencesUtils.setParam(SMSLoginActivity.this, Global.LOGIN_TYPE, Global.LOGIN_TYPE_TEL);
                    Handler handlerForSMSLogin = Global.handlerForSMSLogin;
                    if (handlerForSMSLogin != null) {
                        Message message = new Message();
                        message.what = 666;
                        Bundle bundle = new Bundle();
                        message.setData(bundle);
                        handlerForSMSLogin.sendMessage(message);
                    }
                } else {
                    if (StringUtils.isEmpty(phone.getText().toString().trim())) {
                        Toast.makeText(SMSLoginActivity.this, "请填写11位手机号码.", Toast.LENGTH_SHORT).show();
                    } else if (StringUtils.isEmpty(provingCode.getText().toString().trim())) {
                        Toast.makeText(SMSLoginActivity.this, "请填写短信验证码.", Toast.LENGTH_SHORT).show();
                    } else {
                        //提交验证
                        SMSSDK.submitVerificationCode("86", phone.getText().toString().trim(), provingCode.getText().toString().trim());
                    }
                }
                break;
            case R.id.smslogiin_layout_rl:
                //返回
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册回调
        SMSSDK.unregisterEventHandler(eh);
    }
}
