package com.tatait.tataweibo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;


public class SwitchView_tip extends View{
    private final Paint paint = new Paint();
    private final Path sPath = new Path();

    private int mWidth,mHeight;
    private float sWidth,sHeight;
    private float sLeft,sTop,sRight,sBottom;
    private float sCenterX,sCenterY;

    private float sAnim,bAnim;
    private boolean isOn;
    private float bTranslateX;
    private final AccelerateInterpolator aInterpolator = new AccelerateInterpolator(2);

    private float bRadius, bStrokWidth;
    private float bWidth;
    private float bLeft, bTop, bRight, bBottom;
    private float sScaleCenterX;
    private float sScale;

    //开关监听器
    private OnSwitchListener onSwitchListener;
    //是否设置了开关监听器
    private boolean isSwitchListenerOn = false;

	public SwitchView_tip(Context context) {
		super(context);
	}

	public SwitchView_tip(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayerType(LAYER_TYPE_SOFTWARE,null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = (int) (widthSize * 0.65f);
		setMeasuredDimension(widthSize,heightSize);
	}

    /**
     * 执行顺序  SwitchView_tip onMeasure onDraw、onSizeChanged，onFinishInflate
     */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        //canvas.drawColor(0xffcccccc);

        paint.setAntiAlias(true);//用来防止边缘的锯齿//mPaint.setBitmapFilter(true)//用来对位图进行滤波处理
        paint.setStyle(Paint.Style.FILL);//Style.FILL_AND_STROKE：同时实心和空心
        paint.setColor(0xffffffff);
		canvas.drawPath(sPath,paint);//画出田径场

        sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0;

        final float asAnim = aInterpolator.getInterpolation(sAnim);

        //1. save、restore 等与层的保存和回滚相关的方法；
        //2. scale、rotate、clipXXX 等对画布进行操作的方法；
        //3. drawXXX 等一系列绘画相关的方法；
        final float scale = sScale * (isOn ? asAnim : 1 - asAnim); //缩放大小参数随sAnim变化而变化
        bTranslateX = sWidth - bWidth;
        final float translate = bTranslateX * (isOn ? 1 - asAnim : asAnim); // 平移距离参数随sAnim变化而变化

        canvas.save();//保存画布的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        //http://blog.csdn.net/tianjian4592/article/details/45234419
        //scale(float sx, float sy, float px, float py)
//        canvas.scale(scale, scale, sCenterX, sCenterY);
        canvas.scale(scale, scale, sScaleCenterX, sCenterY);
        paint.setColor(0xffcccccc);
        canvas.drawPath(sPath, paint);
        canvas.restore();//用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。

        //按钮
        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffffffff);
        canvas.translate(translate, 0);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮白底
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffdddddd);
        paint.setStrokeWidth(bStrokWidth);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮灰边
        canvas.restore();

        paint.reset();
        if (sAnim > 0) invalidate(); // 继续重绘
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;//视图自由宽度
        mHeight = h;//视图自由高达
        sLeft = sTop = 0;// 田径场 左和上的坐标
        sRight = mWidth;//田径场 右占自身的全部
//        sBottom = mHeight * 0.8f;// 田径场 底部占全身的80%，下面预留20%画按钮阴影
        sBottom = mHeight;// 田径场 底部占全身
        sWidth = sRight - sLeft;//田径场的宽度
        sHeight = sBottom - sTop;//田径场的高度
        sCenterX = (sRight + sLeft) / 2;//田径场的X轴中心坐标
        sCenterY = (sBottom + sTop) / 2;//田径场的Y轴中心坐标

        //RectF 包含一个矩形的四个单精度浮点坐标【左上角的坐标(left,top)，右下角的坐标(right,bottom)】
        RectF sRectF = new RectF(sLeft,sTop,sBottom,sBottom);
        //Path.arcTo(RectF:弧线--画什么, startAngle开始的角度--0度表示X轴正坐标，sweepAngle旋转的度数)
        sPath.arcTo(sRectF,90,180);
        sRectF.left = sRight - sBottom;
        sRectF.right = sRight;
        sPath.arcTo(sRectF,270,180);
        sPath.close(); //path 准备田径场的路径
        //它这个是先画【左下角（0,0），右上角(0.8H,0.8H)的正方形的弧线】，再画【左下角（W-0.8H,0),右上角（W，0.8H）的另一半弧线】

        //s开头的表示田径场，b开头的表示按钮。
        bLeft = bTop = 0;
        bRight = bBottom = sBottom; // 和田径场同高，同宽的节奏， 包裹圆形的是个正方形。
        bWidth = bRight - bLeft;
        final float halfHeightOfS = (sBottom - sTop) / 2;
        bRadius = halfHeightOfS * 0.9f; // 按钮的半径
        bStrokWidth = 2 * (halfHeightOfS - bRadius); // 按钮的边框

        sScale = 1;
//        sScale = 1 - bStrokWidth / sHeight;
        sScaleCenterX = sWidth - halfHeightOfS;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                sAnim = 1; // 动画标示
                isOn = !isOn; // 状态标示 ， 开关
                //如果设置了监听器，则调用此方法
                if(isSwitchListenerOn) {
                    onSwitchListener.onSwitched(isOn);
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @return the state of switch view
     */
    public boolean isOpened() {
        return isOn;
    }

    /**
     * if set true , the state change to on;
     * if set false, the state change to off
     *
     * @param isOpened
     */
    public void setOpened(boolean isOpened) {
        isOn = isOpened; // 状态标示 ， 开关
        invalidate();
    }

    public void setOnSwitchListener(OnSwitchListener listener) {
        onSwitchListener = listener;
        isSwitchListenerOn = true;
    }


    public interface OnSwitchListener {
        void onSwitched(boolean isSwitchOn);
    }
}
