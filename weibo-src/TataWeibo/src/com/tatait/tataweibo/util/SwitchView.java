package com.tatait.tataweibo.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * For details, please see <b>http://blog.csdn.net/bfbx5173/article/details/45191147<b>
 *
 * 功能超级完善的滑动
 * @author else
 */
public class SwitchView extends View {

	private final Paint paint = new Paint();
	private final Path sPath = new Path();
	private final Path bPath = new Path();
	private final RectF bRectF = new RectF();
	private float sAnim, bAnim;
	private RadialGradient shadowGradient;
	private final AccelerateInterpolator aInterpolator = new AccelerateInterpolator(2);

	/**
	 * state switch on      // 已经打开
	 */
	public static final int STATE_SWITCH_ON = 4;
	/**
	 * state prepare to off // 准备关闭
	 */
	public static final int STATE_SWITCH_ON2 = 3;
	/**
	 * state prepare to on  // 准备打开
	 */
	public static final int STATE_SWITCH_OFF2 = 2;
	/**
	 * state switch off // 已经关闭
	 */
	public static final int STATE_SWITCH_OFF = 1;
	/**
	 * current state
	 */
	private int state = STATE_SWITCH_OFF;
	/**
	 * last state
	 */
	private int lastState = state;

	private boolean isOpened = false;

	private int mWidth, mHeight;
	private float sWidth, sHeight;
	private float sLeft, sTop, sRight, sBottom;
	private float sCenterX, sCenterY;
	private float sScale;

	private float bOffset;
	private float bRadius, bStrokeWidth;
	private float bWidth;
	private float bLeft, bTop, bRight, bBottom;
	private float bOnLeftX, bOn2LeftX, bOff2LeftX, bOffLeftX;

	private float shadowHeight;

	public SwitchView(Context context) {
		this(context, null);
	}

	public SwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = (int) (widthSize * 0.65f);//153/99  0.65
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;//视图自由宽度
        mHeight = h;//视图自由高达

        sLeft = sTop = 0;// 田径场 左和上的坐标
        sRight = mWidth;//田径场 右占自身的全部
        sBottom = mHeight * 0.91f;// 田径场 底部占全身的91%，下面预留19%画按钮阴影
        sWidth = sRight - sLeft;//田径场的宽度
        sHeight = sBottom - sTop;//田径场的高度
        sCenterX = (sRight + sLeft) / 2;//田径场的X轴中心坐标
        sCenterY = (sBottom + sTop) / 2;//田径场的Y轴中心坐标
		shadowHeight = mHeight - sBottom;//阴影的高度

        //s开头的表示田径场，b开头的表示按钮。
		bLeft = bTop = 0;
		bRight = bBottom = sBottom;// 和田径场同高，同宽的节奏， 包裹圆形的是个正方形。
		bWidth = bRight - bLeft;
		final float halfHeightOfS = (sBottom - sTop) / 2;
		bRadius = halfHeightOfS * 0.95f;// 按钮的半径
		bOffset = bRadius * 0.2f; // 有多瘪~  半径的五分之一左右
		bStrokeWidth = (halfHeightOfS - bRadius) * 2;// 按钮的边框

		bOnLeftX = sWidth - bWidth;  // 在已经开启状态下，按钮距离自身左端的距离
		bOn2LeftX = bOnLeftX - bOffset;// 在准备关闭状态下，按钮距离自身左端的距离
		bOffLeftX = 0;// 在已经关闭状态下，按钮距离自身左端的距离
		bOff2LeftX = 0;// 在准备开启状态下，按钮距离自身左端的距离

		sScale = 1 - bStrokeWidth / sHeight;//为了完美适配各种分辨率
        //它这个是先画【左下角（0,0），右上角(0.8H,0.8H)的正方形的弧线】，再画【左下角（W-0.8H,0),右上角（W，0.8H）的另一半弧线】
        //RectF 包含一个矩形的四个单精度浮点坐标【左上角的坐标(left,top)，右下角的坐标(right,bottom)】
		RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
        //Path.arcTo(RectF:弧线--画什么, startAngle开始的角度--0度表示X轴正坐标，sweepAngle旋转的度数)
		sPath.arcTo(sRectF, 90, 180);
		sRectF.left = sRight - sBottom;
		sRectF.right = sRight;
		sPath.arcTo(sRectF, 270, 180);
		sPath.close();//path 准备田径场的路径

		bRectF.left = bLeft;// 替代 circle性质的按钮，改为path性质的按钮，以提供“变瘪”的功能
		bRectF.right = bRight;
		bRectF.top = bTop + bStrokeWidth / 2;
		bRectF.bottom = bBottom - bStrokeWidth / 2;

		shadowGradient = new RadialGradient(bWidth / 2, bWidth / 2, bWidth / 2, 0xff000000, 0x00000000, Shader.TileMode.CLAMP);
	}

	private void calcBPath(float percent) {
		bPath.reset();
		bRectF.left = bLeft + bStrokeWidth / 2;
		bRectF.right = bRight - bStrokeWidth / 2;
		bPath.arcTo(bRectF, 90, 180);
		bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2;
		bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2;
		bPath.arcTo(bRectF, 270, 180);
		bPath.close();
	}

	private float calcBTranslate(float percent) {
		float result = 0;
		int wich = state - lastState;
		switch (wich) {
			case 1:
				// off -> off2
				if (state == STATE_SWITCH_OFF2) {
					result = bOff2LeftX - (bOff2LeftX - bOffLeftX) * percent;
				}
				// on2 -> on
				else if (state == STATE_SWITCH_ON) {
					result = bOnLeftX - (bOnLeftX - bOn2LeftX) * percent;
				}
				break;
			case 2:
				// off2 -> on
				if (state == STATE_SWITCH_ON) {
					result = bOnLeftX - (bOnLeftX - bOff2LeftX) * percent;
				}
				// off -> on2
				else if (state == STATE_SWITCH_ON) {
					result = bOn2LeftX - (bOn2LeftX - bOffLeftX) * percent;
				}
				break;
			case 3: // off -> on
				result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent;
				break;
			case -1:
				// on -> on2
				if (state == STATE_SWITCH_ON2) {
					result = bOn2LeftX + (bOnLeftX - bOn2LeftX) * percent;
				}
				// off2 -> off
				else if (state == STATE_SWITCH_OFF) {
					result = bOffLeftX + (bOff2LeftX - bOffLeftX) * percent;
				}
				break;
			case -2:
				// on2 -> off
				if (state == STATE_SWITCH_OFF) {
					result = bOffLeftX + (bOn2LeftX - bOffLeftX) * percent;
				}
				// on -> off2
				else if (state == STATE_SWITCH_OFF2) {
					result = bOff2LeftX + (bOnLeftX - bOff2LeftX) * percent;
				}
				break;
			case -3: // on -> off
				result = bOffLeftX + (bOnLeftX - bOffLeftX) * percent;
				break;
		}

		return result - bOffLeftX;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        //canvas.drawColor(0xffcccccc);
		paint.setAntiAlias(true);//用来防止边缘的锯齿//mPaint.setBitmapFilter(true)//用来对位图进行滤波处理
		final boolean isOn = (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2);
		// draw background
		paint.setStyle(Style.FILL);//Style.FILL_AND_STROKE：同时实心和空心
		paint.setColor(isOn ? 0xff4bd763 : 0xffe3e3e3);
		canvas.drawPath(sPath, paint);//画出田径场

        //迅速的加速和平滑的减速会感到自然和愉快
		sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0;
		bAnim = bAnim - 0.1f > 0 ? bAnim - 0.1f : 0;

		final float dsAnim = aInterpolator.getInterpolation(sAnim);
		final float dbAnim = aInterpolator.getInterpolation(bAnim);
        //1. save、restore 等与层的保存和回滚相关的方法；
        //2. scale、rotate、clipXXX 等对画布进行操作的方法；
        //3. drawXXX 等一系列绘画相关的方法；
		// draw background animation
		final float scale = sScale * (isOn ? dsAnim : 1 - dsAnim);//缩放大小参数随sAnim变化而变化
		final float scaleOffset = (bOnLeftX + bRadius - sCenterX) * (isOn ? 1 - dsAnim : dsAnim);
		canvas.save();//保存画布的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        //http://blog.csdn.net/tianjian4592/article/details/45234419
        //scale(float sx, float sy, float px, float py)
//        canvas.scale(scale, scale, sCenterX, sCenterY);
		canvas.scale(scale, scale, sCenterX + scaleOffset, sCenterY);// 田径场动画的缩放中心
		paint.setColor(0xffffffff);
		canvas.drawPath(sPath, paint);
		canvas.restore();//用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
		// draw center bar
		canvas.save();
		canvas.translate(calcBTranslate(dbAnim), shadowHeight);// 根据anim标示计算按钮开关平移的坐标
		final boolean isState2 = (state == STATE_SWITCH_ON2 || state == STATE_SWITCH_OFF2);
		calcBPath(isState2 ? 1 - dbAnim : dbAnim);// 根据anim标示计算变瘪的按钮路径
		// draw shadow
		paint.setStyle(Style.FILL);
		paint.setColor(0xff333333);
		paint.setShader(shadowGradient);
		canvas.drawPath(bPath, paint);
		paint.setShader(null);
		canvas.translate(0, -shadowHeight);

		canvas.scale(0.98f, 0.98f, bWidth / 2, bWidth / 2);
		paint.setStyle(Style.FILL);
		paint.setColor(0xffffffff);
		canvas.drawPath(bPath, paint);

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(bStrokeWidth * 0.5f);

		paint.setColor(isOn ? 0xff4ada60 : 0xffbfbfbf);
		canvas.drawPath(bPath, paint);

		canvas.restore();

		paint.reset();
		if (sAnim > 0 || bAnim > 0) invalidate();// 重绘的标示由1个变为了2个。
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((state == STATE_SWITCH_ON || state == STATE_SWITCH_OFF) && (sAnim * bAnim == 0)) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					return true;
				case MotionEvent.ACTION_UP:
					lastState = state;
					if (state == STATE_SWITCH_OFF) {
						refreshState(STATE_SWITCH_OFF2);
					} else if (state == STATE_SWITCH_ON) {
						refreshState(STATE_SWITCH_ON2);
					}
					bAnim = 1;
					invalidate();

					if (state == STATE_SWITCH_OFF2) {
						listener.toggleToOn(this);
					} else if (state == STATE_SWITCH_ON2) {
						listener.toggleToOff(this);
					}
					break;
			}
		}
		return super.onTouchEvent(event);
	}
    // 刷新UI前都会经过这里，当UI状态确定为STATE_SWITCH_ON或者STATE_SWITCH_OFF的时候更改逻辑状态。
	private void refreshState(int newState) {
		if (!isOpened && newState == STATE_SWITCH_ON) {
			isOpened = true;
		} else if (isOpened && newState == STATE_SWITCH_OFF) {
			isOpened = false;
		}
		lastState = state;
		state = newState;
		postInvalidate();
	}

	/**
	 * @return the state of switch view
	 */
	public boolean isOpened() {
		return isOpened;
	}

	/**
	 * if set true , the state change to on;
	 * if set false, the state change to off
	 *
	 * @param isOpened
	 */
	public void setOpened(boolean isOpened) {
		refreshState(isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF);
	}

	/**
	 * if set true , the state change to on;
	 * if set false, the state change to off
	 * <br><b>change state with animation</b>
	 *
	 * @param isOpened
	 */
	public void toggleSwitch(final boolean isOpened) {
        // 在这里。提供给调用者的公开方法，传入参数isOpened的值就是调用者所希望立马设置的状态。所以这里直接赋值。
        // 不必要也不能等到refreshState[UI刷新的时候]的时候再赋值。
        // 这样在按钮变瘪的时候发生了意外，再次进入也可以显示用户期望的逻辑状态。
		this.isOpened = isOpened;
		postDelayed(new Runnable() {
			@Override
			public void run() {
				toggleSwitch(isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF);
			}
		}, 300);
	}

	private synchronized void toggleSwitch(int wich) {
		if (wich == STATE_SWITCH_ON || wich == STATE_SWITCH_OFF) {
			if ((wich == STATE_SWITCH_ON && (lastState == STATE_SWITCH_OFF || lastState == STATE_SWITCH_OFF2))
					|| (wich == STATE_SWITCH_OFF && (lastState == STATE_SWITCH_ON || lastState == STATE_SWITCH_ON2))) {
				sAnim = 1;
			}
			bAnim = 1;
			refreshState(wich);
		}
	}

	public interface OnStateChangedListener {
		void toggleToOn(View view);

		void toggleToOff(View view);
	}

	private OnStateChangedListener listener = new OnStateChangedListener() {
		@Override
		public void toggleToOn(View view) {
			toggleSwitch(STATE_SWITCH_ON);
		}

		@Override
		public void toggleToOff(View view) {
			toggleSwitch(STATE_SWITCH_OFF);
		}
	};

	public void setOnStateChangedListener(OnStateChangedListener listener) {
		if (listener == null) throw new IllegalArgumentException("empty listener");
		this.listener = listener;
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.isOpened = isOpened;
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		this.isOpened = ss.isOpened;
		this.state = this.isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
	}

	static final class SavedState extends BaseSavedState {
		private boolean isOpened;

		SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			isOpened = 1 == in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(isOpened ? 1 : 0);
		}
	}
}
