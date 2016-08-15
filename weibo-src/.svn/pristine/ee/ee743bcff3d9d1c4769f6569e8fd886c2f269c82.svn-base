package com.tatait.tataweibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tatait.tataweibo.bean.Constants;
import com.tatait.tataweibo.bean.FirstEvent;
import com.tatait.tataweibo.service.MusicService;
import com.tatait.tataweibo.util.Global;
import com.tatait.tataweibo.util.MySlipSwitch;
import com.tatait.tataweibo.util.SharedPreferencesUtils;
import com.tatait.tataweibo.util.Tools;
import com.tatait.tataweibo.util.show.CircularImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends Activity {
    /**
     * 画一个几何图形
     * hades
     *  蓝色着衣
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
    }

    public class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            // 设置画布的背景颜色
            canvas.drawColor(Color.WHITE);
            /**
             * 定义矩形为空心
             */
            // 定义画笔1
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            // 消除锯齿
            paint.setAntiAlias(true);
            // 设置画笔的颜色
            paint.setColor(Color.RED);
            // 设置paint的外框宽度
            paint.setStrokeWidth(2);

            // 画一个圆
            canvas.drawCircle(40, 30, 20, paint);
            // 画一个正放形
            canvas.drawRect(20, 70, 70, 120, paint);
            // 画一个长方形
            canvas.drawRect(20, 130, 90, 170, paint);
            // 画一个椭圆
            RectF re = new RectF(20, 190, 100, 230);
            canvas.drawOval(re, paint);

            /**
             * 定义矩形为实心
             */
            paint.setStyle(Paint.Style.FILL);
            // 定义画笔2
            Paint paint2 = new Paint();
            // 消除锯齿
            paint2.setAntiAlias(true);
            // 设置画笔的颜色
            paint2.setColor(Color.BLUE);
            // 画一个空心圆
            canvas.drawCircle(150, 30, 20, paint2);
            // 画一个正方形
            canvas.drawRect(130, 70, 180, 120, paint2);
            // 画一个长方形
            canvas.drawRect(130, 130, 200, 180, paint2);
            // 画一个椭圆形
            RectF re2 = new RectF(130, 200, 200, 240);
            canvas.drawOval(re2, paint2);
        }
    }
}
