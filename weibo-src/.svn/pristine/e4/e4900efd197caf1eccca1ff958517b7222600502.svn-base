package com.tatait.tataweibo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.tatait.tataweibo.bean.ShareBean;
import com.tatait.tataweibo.bean.UserInfo;
import com.tatait.tataweibo.dao.UserDao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author lynn
 */
public class CommonUtil {
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 20) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 2 * 获取版本号 3 * @return 当前应用的版本号 4
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得屏幕的宽度
     *
     * @param context 上下文
     * @return 屏幕的宽度 单位 像素
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
                    0, 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {

            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 开启activity 带参数
     *
     * @param context
     * @param activity
     * @param bundle
     */
    public static void jumpIntent(Context context, Class<?> activity,
                                  Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    public static String packageName = Global.app_name;

    /**
     * 判断指定包名的进程是否运行
     *
     * @param context
     * @param context 指定包名
     * @return 是否运行
     */
    public static boolean isRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        String MY_PKG_NAME = packageName;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                return true;
            }
        }
        return false;
    }

    // 复制微信号到黏贴板
    @SuppressLint("NewApi")
    public static String copyWeiXinNumber(Context context, String bdwxmix) {
        String tip = "";
        String[] bd = bdwxmix.split("&");
        String bdgender = bd[1];
        if (Integer.parseInt(bdgender) == -1) {
            tip = "QQ:" + bd[0] + "\n添加后请耐心等待\n对方会及时通过请求";
        } else if (Integer.parseInt(bdgender) == 1) {
            tip = "QQ:" + bd[0] + "\n添加后请耐心等待\n对方会及时通过请求";
        } else {
            tip = "QQ:" + bd[0] + "\n添加后请耐心等待\n对方会及时通过请求";
        }
        ClipboardManager clipboard = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", bd[0]);
        clipboard.setPrimaryClip(clip);
        return tip;
    }

    // 复制微信号到黏贴板
    @SuppressLint("NewApi")
    public static String copyWeiXinNumber2(Context context, String bdwxmix) {
        String tip = "";
        String[] bd = bdwxmix.split("&");
        String bdgender = bd[1];
        if (Integer.parseInt(bdgender) == -1) {
            tip = "已为您成功复制\n" + "QQ:" + bd[0] + "\n亲,请及时联系对方哦！";
        } else if (Integer.parseInt(bdgender) == 1) {
            tip = "已为您成功复制\n" + "QQ:" + bd[0] + "\n亲,请及时联系对方哦！";
        } else {
            tip = "已为您成功复制\n" + "QQ:" + bd[0] + "\n亲,请及时联系对方哦！";
        }
        ClipboardManager clipboard = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", bd[0]);
        clipboard.setPrimaryClip(clip);
        return tip;
    }

    public static void startAPP(Context context, String appPackageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show("没有安装客户端");
        }
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            if (!tasks.get(0).topActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * sharedSDK
     * @param shareBean
     * @param context
     */
    public static void showShare(ShareBean shareBean,Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareBean.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareBean.getTitleUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBean.getText());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareBean.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(shareBean.getComment());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(packageName);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareBean.getSiteUrl());

        // 启动分享GUI
        oks.show(context);
    }

    /**
     * Umeng 分享
     * @param shareBean
     * @param context
     */
    public static void showUmengShare(ShareBean shareBean,Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareBean.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareBean.getTitleUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBean.getText());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareBean.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(shareBean.getComment());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(packageName);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareBean.getSiteUrl());

        // 启动分享GUI
        oks.show(context);
    }

    public static boolean isLogin(Context mContext){
        //游客登录
        if(Global.LOGIN_TYPE_YK.equals(SharedPreferencesUtils.getParam(mContext, Global.LOGIN_TYPE, Global.LOGIN_TYPE_NULL))){
            return false;
        }else {
            UserDao dao = new UserDao(mContext);
            List<UserInfo> userData = dao.findAllUser();
            if (userData != null && !userData.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
