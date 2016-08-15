package com.tatait.tataweibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tatait.tataweibo.MainActivity.UserSession;
import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.util.AccessTokenKeeper;
import com.tatait.tataweibo.util.HttpUtils;
import com.tatait.tataweibo.util.StringUtils;
import com.tatait.tataweibo.util.ToastUtil;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.file.FileService;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发微博（图 文）
 *
 * @author WSXL
 */
public class WriterWeiboActivity extends Activity {


    @Bind(R.id.home_back_bar_back)
    ImageView home_back_bar_back;
    @Bind(R.id.home_back_title)
    TextView txt_wb_title;
    @Bind(R.id.home_back_title_menu)
    Button writer_weibo;
    @Bind(R.id.weibo_txt)
    EditText weibo_txt;
    @Bind(R.id.writer_images)
    ImageView writer_images;
    @Bind(R.id.tv_text_limit)
    TextView tv_text_limit;
    @Bind(R.id.upImage_btn)
    Button upImage_btn;
    private Oauth2AccessToken mAccessToken;
    // 发送图片的路径
    private String image_path = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writer);
        ButterKnife.bind(this);
        // 设置当前登录人
        UserInfo user = UserSession.nowUser;
        MyClick click = new MyClick();
        writer_weibo.setOnClickListener(click);
        upImage_btn.setOnClickListener(click);
        home_back_bar_back.setOnClickListener(click);
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 注册输入框内容监听器
        weibo_txt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean flag = false;
                // 获得输入框的内容
                String mText = weibo_txt.getText().toString();
                int len = mText.length();// 获得输入框的内容长度
                if (len == 0) {
                    if (writer_weibo.getVisibility() == View.VISIBLE) {// 判断发送按钮是不是启用状态
                        writer_weibo.setVisibility(View.GONE);// 隐藏发送按钮
                    }
                } else if (len <= Constants.WEIBO_MAX_LENGTH) {// 比较已经输入的内容长度是不是超过了规定的长度（140）
                    len = Constants.WEIBO_MAX_LENGTH - len;// 计算还允许输入内容个数
                    tv_text_limit.setTextColor(Color.GREEN);// 设置提示text颜色
                    if (writer_weibo.getVisibility() == View.GONE) {// 判断发送按钮是不是启用状态
                        writer_weibo.setVisibility(View.VISIBLE);// 显示发送按钮
                    }
                } else {
                    len = len - Constants.WEIBO_MAX_LENGTH;// 计算输入内容超过允许输入的个数
                    tv_text_limit.setTextColor(Color.RED);// 设置提示text颜色
                    if (writer_weibo.getVisibility() == View.VISIBLE) {// 判断发送按钮是不是启用状态
                        writer_weibo.setVisibility(View.GONE);// 隐藏发送按钮
                    }
                    flag = true;
                }
                tv_text_limit.setText(flag ? "-" + len : String.valueOf(len));// 设置允许输入内容个数提示内容
            }

            /**
             * 当输入框内容改变后执行
             */
            public void afterTextChanged(Editable s) {
            }

            /**
             * 当输入框内容改前执行
             */
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

    }

    class MyClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.upImage_btn:// 添加图片
                    imageChooseItem(Constants.meun_items);
                    break;
                case R.id.home_back_title_menu:// 发送微博
                    int flag = 1;// 1:表示发送文字微博 2:表示发送图文微博
//                    if (!"".equals(image_path)) {
//                        flag = 2;
//                        params.add(new BasicNameValuePair("pic", image_path));
//                    }
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("status", weibo_txt.getText().toString());
                    requestParams.put("source", Constants.APP_KEY);
                    requestParams.put("access_token", mAccessToken.getToken());
                    String url = "";
                    switch (flag) {
                        case 1:
                            url = Constants.WEIBO_WRITER_TXT;
                            break;
                        case 2:
                            url = Constants.WEIBO_WRITER_TXT_IMG;
                            break;
                    }

                    httpGetMethod(url, requestParams);
                    break;
                case R.id.home_back_bar_back:// 返回按钮
//                    Intent intent = new Intent(WriterWeiboActivity.this,HomeActivity.class);
//                    startActivity(intent);
                    WriterWeiboActivity.this.finish();
                    break;
            }

        }
    }

    private void httpGetMethod(String url, RequestParams requestParams) {
        HttpUtils.postRequest(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.show(R.string.notice_get_userinfo_fail);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String created_at = "";
                try {
                    JSONObject json = new JSONObject(response.toString());
                    created_at = json.getString("created_at");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!StringUtils.isEmpty2(created_at)) {
                    Toast.makeText(WriterWeiboActivity.this, WriterWeiboActivity.this.getResources().getString(
                            R.string.send_sucess), Toast.LENGTH_LONG).show();
                    WriterWeiboActivity.this.finish();
                } else {
                    Toast.makeText(WriterWeiboActivity.this, WriterWeiboActivity.this.getResources().getString(
                            R.string.send_failed), Toast.LENGTH_LONG).show();
                }
                super.onSuccess(statusCode, headers, response);
            }

        });
    }

    /**
     * 表示去sdcard获得图片
     */
    private static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /**
     * 表示去相机获得图片
     */
    private static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    private String thisLarge = null;

    /**
     * 操作选择
     *
     * @param items
     */
    public void imageChooseItem(CharSequence[] items) {
        AlertDialog imageDialog = new AlertDialog.Builder(this)
                .setTitle("添加图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 手机选图
                        if (item == 0) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYSDCARD);
                        } else if (item == 1) {// 拍照
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                            String camerName = Tools.getFileName();
                            String fileName = "facejava-" + camerName + ".png";

                            File camerFile = new File(Tools.getCamerPath(), fileName);

                            image_path = camerFile.getPath();
                            thisLarge = Tools.getLatestImage(WriterWeiboActivity.this);

                            Uri originalUri = Uri.fromFile(camerFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
                            startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);
                        } else if (item == 2) {
                            thisLarge = null;
                            writer_images.setImageBitmap(null);
                            image_path = "";
                            writer_images.setVisibility(View.GONE);
                        }
                    }
                }).create();
        imageDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
            if (resultCode != RESULT_OK) {
                return;
            }
            if (data == null) {
                return;
            }
            String[] imageArray = Tools.getAbsoluteImagePath(this,
                    data.getData());

            String suffix = FileService.getFileFormat(imageArray[1]);
            if (suffix.equals("") || !Constants.imageType.containsKey(suffix)) {
                Toast.makeText(this, "请选择图片文件！", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(imageArray[1]);
            Bitmap bitmap_Image = BitmapFactory.decodeFile(file.getPath());
            if (bitmap_Image != null) {
                writer_images.setVisibility(View.VISIBLE);
                writer_images.setImageBitmap(bitmap_Image);
                image_path = file.getPath();
            }
        } else if (requestCode == REQUEST_CODE_GETIMAGE_BYCAMERA) {// 拍摄图片
            File file = new File(image_path);
            Bitmap bitmap_Image = BitmapFactory.decodeFile(file.getPath());
            if (bitmap_Image != null) {
                writer_images.setVisibility(View.VISIBLE);
                writer_images.setImageBitmap(bitmap_Image);
            }
        }

        writer_images.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(thisLarge)), "image/*");
                startActivity(intent);
            }
        });

        super.onActivityResult(requestCode, resultCode, data);

    }

    // 主菜单点击返回键，弹出对话框
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent(WriterWeiboActivity.this,
                    HomeActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
