package com.tatait.tataweibo.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tatait.tataweibo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 网络请求类
 *
 * @author Lynn
 */
public class HttpUtils {
    /**
     * 网络链接超时时间
     */
    private static final int TIME_OUT = 60 * 1000;
    private static HttpUtils mInstance;
    protected Dialog progressDialogBar;
    private static AsyncHttpClient mClient;
    public static Handler handler;
    private static int responsecode = 0;

    private static String is_superuser = "0";

    private HttpUtils() {
        initHttpClient();
    }

    public synchronized static HttpUtils getInstance() {
        if (null == mInstance)
            mInstance = new HttpUtils();
        return mInstance;
    }

    private void initHttpClient() {
        mClient = new AsyncHttpClient();
        mClient.setTimeout(TIME_OUT);
    }

    /**
     * 以POST带参数的方式请求数据
     *
     * @param route
     * 路由内容
     * @param params
     * 请求所需参数
     * @param responseHandler
     * 回调方式
     */
    private static Context paramContext;
    private static AsyncHttpClient client;

    public static Context getParamContext() {
        return paramContext;
    }

    public static void setParamContext(Context paramContext) {
        HttpUtils.paramContext = paramContext;
    }

    public static void postRequest(String route, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = getInstance(paramContext);
        client.setTimeout(TIME_OUT);
        if (responseHandler != null) {
            Log.v("url", route);
            if (params == null) {
                client.post(route, responseHandler);
            } else {
                client.post(route, params, responseHandler);
            }
        }
    }

    public static AsyncHttpClient getInstance(Context paramContext) {
        if (client == null) {
            client = new AsyncHttpClient();
            // 将client的Cookie保存到一个PersistentCookieStore
            // PersistentCookieStore myCookieStore = new PersistentCookieStore(
            // paramContext);
            // client.setCookieStore(myCookieStore);
        }
        return client;
    }

    public static void postRequest(String route, AsyncHttpResponseHandler responseHandler) {
        postRequest(route, null, responseHandler);
    }

    /**
     * 以GET不带参数的方式请求数据
     *
     * @param route           路由内容
     * @param responseHandler 回调方式
     */
    public static void getRequest(String route, AsyncHttpResponseHandler responseHandler) {
        client = getInstance(paramContext);
        client.setTimeout(TIME_OUT);
        // client.addHeader("X-Requested-With", "XMLHttpRequest");
        if (responseHandler != null) {
            client.get(route, responseHandler);
//            Log.v("url", route);
        }
    }

    public static void getRequest(String route, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = getInstance(paramContext);
        client.setTimeout(TIME_OUT);
        if (responseHandler != null) {
            client.get(route, params, responseHandler);
//            Log.v("url", route);
        }
    }

    public static void submit_Data(final List<NameValuePair> params, final int type) {
        new Thread() {
            @Override
            public void run() {
                //type  0 表示更新用户信息  1表示反馈建议  2表示举报 3表示发布投稿  4表示申请成功搞手
                if (type == 0) {
                    UpdatePerson(params);
                } else if (type == 2) {
                    Report(params);
                } else if (type == 3) {
//                    submitPic(params);
                } else if (type == 4) {
                    tobegaoshou(params);
                } else if (type == 5) {
                    renzheng(params);
                } else if (type == 6) {
                    tijiaojietu(params);
                } else if (type == 7) {
                    pingjia(params);
                } else if (type == 8) {
                    wxpay(params);
                } else if (type == 9) {
                    submitVideo(params);
                } else if (type == 10) {
                    submitQiuliao(params);
                } else {
                    FeekBack(params);
                }

            }
        }.start();
    }

    private static void wxpay(List<NameValuePair> params) {
//        params.add(new BasicNameValuePair("appid", Constants.APP_ID));0
//        params.add(new BasicNameValuePair("body", "5朵'玫瑰'"));1
//        params.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));2
//        params.add(new BasicNameValuePair("nonce_str", Integer.valueOf(mathrand).toString()));3
//        params.add(new BasicNameValuePair("notify_url", HttpRoute.URL_HUIDIAOADDR));4
//        params.add(new BasicNameValuePair("out_trade_no", tt + "" + Integer.valueOf(mathrand).toString()));5
//        params.add(new BasicNameValuePair("spbill_create_ip", getLocalIpAddress()));6
//        params.add(new BasicNameValuePair("total_fee", "0.01"));7
//        params.add(new BasicNameValuePair("trade_type", "APP"));8
//        params.add(new BasicNameValuePair("sign", findSign(params)));9
//        StringBuilder xml = new StringBuilder();
////        String nonce_str = getRandomString(32);
////        String ip = "127.0.0.1";// 客户端IP自己处理
//        JSONObject jso = new JSONObject();
//        String prepay_id = "", sign = "";
//        try {
////            xml.append("appid=").append(params.get(0).getValue()).append("&body=").append(params.get(1).getValue());
////            xml.append("&mch_id=").append(params.get(2).getValue()).append("&nonce_str=").append(params.get(3).getValue());
////            xml.append("&notify_url=").append(params.get(4).getValue()).append("&out_trade_no=").append(params.get(5).getValue()).append("&spbill_create_ip=").append(params.get(6).getValue());
////            xml.append("&total_fee=").append(params.get(7).getValue()).append("&trade_type=").append(params.get(8).getValue()).append("&sign=").append(params.get(9).getValue());
////            sign = new Util().MD5Purity(xml.toString()).toUpperCase();// MD5加密签名加密类自己解决就不放上来了
//            xml.delete(0, xml.length());
//            xml.append("<xml>");
//            xml.append("   <appid>").append(params.get(0).getValue()).append("</appid>");
//            xml.append("   <body>").append(params.get(1).getValue()).append("</body>");
//            xml.append("   <mch_id>").append(params.get(2).getValue()).append("</mch_id>");
//            xml.append("   <nonce_str>").append(params.get(3).getValue()).append("</nonce_str>");
//            xml.append("   <notify_url>").append(params.get(4).getValue()).append("</notify_url>");
//            xml.append("   <out_trade_no>").append(params.get(5).getValue()).append("</out_trade_no>");
//            xml.append("   <spbill_create_ip>").append(params.get(6).getValue()).append("</spbill_create_ip>");
//            xml.append("   <total_fee>").append(params.get(7).getValue()).append("</total_fee>");
//            xml.append("   <trade_type>").append(params.get(8).getValue()).append("</trade_type>");
//            xml.append("   <sign>").append(params.get(9).getValue()).append("</sign>");
//            xml.append("</xml>");
//            HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            StringEntity entity = new StringEntity(xml.toString(), "UTF-8");
//            entity.setContentEncoding("utf-8");
//            entity.setContentType("text/xml");
//            post.setEntity(entity);
//            String ss = EntityUtils.toString(new DefaultHttpClient().execute(post).getEntity(), "utf-8");
//            String sss = ss;
////            JSONArray childNodes = JSONML.toJSONObject(EntityUtils.toString(new DefaultHttpClient().execute(post).getEntity(), "utf-8")).getJSONArray(
////                    "childNodes");
////            System.out.println(childNodes);
////            int len = childNodes.length() - 1;
////            for (int i = len; i > -1; i--) {
////                JSONObject js = childNodes.getJSONObject(i);
////                if (js.get("tagName").equals("prepay_id")) {
////                    prepay_id = js.getJSONArray("childNodes").getString(0);
////                    break;
////                }
////            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        jso.put("sign", sign);
////        jso.put("appid", appid);
////        jso.put("noncestr", nonce_str);
////        jso.put("package", "Sign=WXPay");
////        jso.put("partnerid", mch_id);
////        jso.put("prepayid", prepay_id);
////        jso.put("timestamp", System.currentTimeMillis());
//        return jso;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_TONGYIDINGDAN);
        try {
            StringBuilder xml = new StringBuilder();
            xml.append("<xml>");
            xml.append("   <appid>").append(params.get(0).getValue()).append("</appid>");
            xml.append("   <body>").append(params.get(1).getValue()).append("</body>");
            xml.append("   <mch_id>").append(params.get(2).getValue()).append("</mch_id>");
            xml.append("   <nonce_str>").append(params.get(3).getValue()).append("</nonce_str>");
            xml.append("   <notify_url>").append(params.get(4).getValue()).append("</notify_url>");
            xml.append("   <out_trade_no>").append(params.get(5).getValue()).append("</out_trade_no>");
            xml.append("   <spbill_create_ip>").append(params.get(6).getValue()).append("</spbill_create_ip>");
            xml.append("   <total_fee>").append(Integer.parseInt(params.get(7).getValue())).append("</total_fee>");
            xml.append("   <trade_type>").append(params.get(8).getValue()).append("</trade_type>");
            xml.append("   <sign>").append(params.get(9).getValue()).append("</sign>");
            xml.append("</xml>");
            StringEntity entity = new StringEntity(xml.toString(), "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            int responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                String prepay_id = Response.substring(Response.indexOf("<prepay_id>") + 20, Response.indexOf("</prepay_id>") - 3);
                String return_code = Response.substring(Response.indexOf("<return_code>") + 22, Response.indexOf("</return_code>") - 3);
                String return_msg = Response.substring(Response.indexOf("<return_msg>") + 21, Response.indexOf("</return_msg>") - 3);
                if ("SUCCESS".equals(return_code)) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 909;
                        Bundle data = new Bundle();
                        data.putString("appid", params.get(0).getValue());
                        data.putString("partnerid", params.get(2).getValue());
                        data.putString("prepayid", prepay_id);
                        data.putString("sign", params.get(9).getValue());
                        data.putString("noncestr", params.get(3).getValue());
                        data.putString("timestamp", new SimpleDateFormat("yyyyMMddHH").format(new Date()));
                        data.putString("package", "Sign=WXPay");
                        message.setData(data);
                        mmhandler.sendMessage(message);
                    }
                } else {
                    ToastUtil.show("生成订单失败,原因：" + return_msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请成为搞手
     *
     * @param params
     */
    private static void tobegaoshou(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_TOBEGAOSHOU);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());

                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 909;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        ToastUtil.show(R.string.notice_apply_fail);
                    } else {
                        Handler mmhandler = Global.handler;
                        if (mmhandler != null) {
                            Message message = new Message();
                            message.what = 908;
                            message.obj = tip;
                            mmhandler.sendMessage(message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交截图
     *
     * @param params
     */
    private static void tijiaojietu(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_COMPLAINT);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 909;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        tip = "提交失败，请稍后重试";
                    }
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 910;
                        message.obj = tip;
                        mmhandler.sendMessage(message);
                    }

                }
            }else{
                Handler mmhandler = Global.handler;
                if (mmhandler != null) {
                    Message message = new Message();
                    message.what = 910;
                    message.obj = "已提交申请";
                    mmhandler.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布求聊
     * @param params
     */
    private static void submitQiuliao(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_FORCHAT);
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("imgs")) {
                    Bitmap bit = BitmapFactory.decodeFile(params.get(index).getValue(),getBitmapOption(2));
                    if(bit.getHeight() > 600){
                        entity.addPart("imgs" + index ,  new FileBody(new File(params.get(index).getValue())));
                    }else {
                        entity.addPart("imgs" + index, new FileBody(yasuoFile(params.get(index).getValue())));
                    }

                } else {
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 909;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        tip = "提交失败，请稍后重试";
                    }
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 910;
                        message.obj = tip;
                        mmhandler.sendMessage(message);
                    }

                }
            }else{
//                Handler mmhandler = Global.handler;
//                if (mmhandler != null) {
//                    Message message = new Message();
//                    message.what = 910;
//                    message.obj = "已提交成功";
//                    mmhandler.sendMessage(message);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 评价
     *
     * @param params
     */
    private static void pingjia(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_FINISHTALK);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart("commentpic", new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 909;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        ToastUtil.show("提交失败，请稍后重试");
                    } else {
                        ToastUtil.show(tip);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新个人信息
     *
     * @param params
     */
    public static void UpdatePerson(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_UPDATE_USER_INFORMATION);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("touxiang_images")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart("image", new FileBody(yasuoFile(params.get(index).getValue())));

                } else if (params.get(index).getName().equalsIgnoreCase("images")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart("image" + index, new FileBody(yasuoFile(params.get(index).getValue())));

                } else if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    try {
                        JSONObject data = jsonobject.getJSONObject("data").getJSONObject("result");
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("username")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.USERNAME, StringUtils.getObjString(data.getString("username")));
                        }
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("img")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.IMAGE_URL, StringUtils.getObjString(data.getString("img")));
                        }
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("gender")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.GENDER, StringUtils.getObjString(data.getString("gender")));
                        }
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("qq")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.QQ, StringUtils.getObjString(data.getString("qq")));
                        }
                        Handler mmhandler = Global.handler;
                        if (mmhandler != null) {
                            Message message = new Message();
                            message.what = 901;
                            message.obj = data.getString("renzheng") == null ? "" : data.getString("renzheng").toString();
                            mmhandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        ToastUtil.show(R.string.notice_update_fail);
                    } else {
                        ToastUtil.show(tip);
                    }
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 902;
                        mmhandler.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            Handler mmhandler = Global.handler;
            if (mmhandler != null) {
                Message message = new Message();
                message.what = 901;
                message.obj = "2";
                mmhandler.sendMessage(message);
            }
            e.printStackTrace();
        }
    }

    /**
     * 认证信息
     *
     * @param params
     */
    public static void renzheng(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_GOTORENZHENG);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName() + index, new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 902;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        ToastUtil.show("提交成功");
                    } else {
                        ToastUtil.show(tip);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反馈意见
     *
     * @param params
     */
    public static void FeekBack(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_FEEDBACK);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());

                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                if (code == 1) {
                    Handler mmhandler = Global.handler;
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 902;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    String tip = jsonobject.optString("info");
                    if (StringUtils.isEmpty2(tip)) {
                        ToastUtil.show(R.string.notice_feedback_fail);
                    } else {
                        ToastUtil.show(tip);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 举报
     *
     * @param params
     */
    public static void Report(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_REPORT);
        try {
            // MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("image")) {
//                    System.out.println("post - if");
                    // If the key equals to "image", we use FileBody to transfer the data
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));

                } else {
//                    System.out.println("post - else");
//                    // Normal string data
//                    System.out.println("输出数据的值" + params.get(index).getName());

                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
//            System.out.println("post - done" + entity);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                Handler mmhandler = Global.handler;
                if (code == 1) {

                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 903;
                        mmhandler.sendMessage(message);
                    }
                } else {
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 904;
                        message.obj = jsonobject.optString("info");
                        mmhandler.sendMessage(message);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小视频提交投稿
     *
     * @param params
     */
    public static void submitVideo(List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(HttpRoute.URL_HEAD + HttpRoute.URL_MYVIDEO);
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (int index = 0; index < params.size(); index++) {
                if (params.get(index).getName().equalsIgnoreCase("videoimage")) {
                    entity.addPart("videoimage", new FileBody(new File(params.get(index).getValue())));
                } else if (params.get(index).getName().equalsIgnoreCase("video")) {
                    entity.addPart(params.get(index).getName(), new FileBody(new File(params.get(index).getValue())));
                } else {
                    entity.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue(), Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            responsecode = response.getStatusLine().getStatusCode();
            if (responsecode == 200) {
                //HttpEntity resEntity = response.getEntity();
                HttpEntity resEntity = response.getEntity();
                String Response = EntityUtils.toString(resEntity);
                Log.d("Response:", Response);
                //Generate the array from the response
                JSONArray jsonarray = new JSONArray("[" + Response + "]");
                JSONObject jsonobject = jsonarray.getJSONObject(0);
                //Get the result variables from response
                int code = jsonobject.optInt("code");
                Handler mmhandler = Global.handler;
                if (code == 1) {
                    try {
                        JSONObject data = jsonobject.getJSONObject("data").getJSONObject("result");
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("videoimage")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.VIDEOIMAGE, data.getString("videoimage"));
                        }
                        if (!StringUtils.isEmpty2(StringUtils.getObjString(data.getString("videoUrl")))) {
                            SharedPreferencesUtils.setParam(paramContext, Global.VIDEOURL, data.getString("videoUrl"));
                        }
                        if (mmhandler != null) {
                            Message message = new Message();
                            message.what = 905;
                            mmhandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (mmhandler != null) {
                        Message message = new Message();
                        message.what = 906;
                        message.obj = jsonobject.optString("info");
                        mmhandler.sendMessage(message);
                    }
                }
            } else {
                Handler mmhandler = Global.handler;
                if (mmhandler != null) {
                    Message message = new Message();
                    message.what = 906;
                    message.obj = "服务器无响应，请稍后重试";
                    mmhandler.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public static File yasuoFile(String path){
        Bitmap bit = BitmapFactory.decodeFile(path,getBitmapOption(2));
//        float pc = (float) 100 / (float) bit.getWidth();//压缩比例
//        Bitmap bit2 = resize_img(bit, pc);//压缩bitmap
        try {
            File ff = new File(path);
            long fileLen1 = ff.length();
            FileInputStream fis = new FileInputStream(ff);
            int fileLen2 = fis.available(); //这就是文件大小
            String s = FileUtils.getFormatSize(fileLen1);
            String s2 = FileUtils.getFormatSize(fileLen2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int fileLen6 = BitmapUtil.getBitmapSize(bit);
        String s6 = FileUtils.getFormatSize(fileLen6);
        Bitmap bit2 = comp(bit);//压缩bitmap

        String filename = path.substring(path.lastIndexOf("/") + 1);
        return saveMyBitmap(filename, bit2);
    }

    //压缩bitmap
    private static Bitmap resize_img(Bitmap bitmap, float pc) {

        Matrix matrix = new Matrix();
        Log.i("mylog2", "缩放比例--" + pc);
        matrix.postScale(pc, pc); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        bitmap.recycle();
        bitmap = null;
        System.gc();

        int width = resizeBmp.getWidth();
        int height = resizeBmp.getHeight();
        Log.i("mylog2", "按比例缩小后宽度--" + width);
        Log.i("mylog2", "按比例缩小后高度--" + height);

        return resizeBmp;
    }

    //将压缩的bitmap保存到sdcard卡临时文件夹img_interim，用于上传
    @SuppressLint("SdCardPath")
    public static File saveMyBitmap(String filename, Bitmap bit) {
        int fileLen7 = BitmapUtil.getBitmapSize(bit);
        String s7 = FileUtils.getFormatSize(fileLen7);
        File dir = new File("/sdcard/img_interim/");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File f = new File("/sdcard/img_interim/" + filename);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bit.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            int fileLen8 = BitmapUtil.getBitmapSize(bit);
            String s8 = FileUtils.getFormatSize(fileLen8);
            fOut.flush();
            fOut.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            f = null;
            e1.printStackTrace();
        }

        return f;
    }

    private static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int fileLen5 = BitmapUtil.getBitmapSize(image);
        String s5 = FileUtils.getFormatSize(fileLen5);
        if (baos.toByteArray().length / 1024 > 100) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        int fileLen3 = BitmapUtil.getBitmapSize(image);
        String s3 = FileUtils.getFormatSize(fileLen3);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        int fileLen4 = BitmapUtil.getBitmapSize(image);
        String s4 = FileUtils.getFormatSize(fileLen4);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
