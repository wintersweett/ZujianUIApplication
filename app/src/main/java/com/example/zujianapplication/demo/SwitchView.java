package com.example.zujianapplication.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.zujianuiapplication.R;

public class SwitchView extends View {

    private static final int STATE_SWITCH_ON = 4;
    private static final int STATE_SWITCH_ON2 = 3;
    private static final int STATE_SWITCH_OFF2 = 2;
    private static final int STATE_SWITCH_OFF = 1;

    private final AccelerateInterpolator interpolator = new AccelerateInterpolator(2);
    private final Paint paint = new Paint();
    private final Path sPath = new Path();
    private final Path bPath = new Path();
    private final RectF bRectF = new RectF();
    private float sAnim, bAnim;
    private RadialGradient shadowGradient;
    //按钮宽高形状比率(0,1] 不推荐大幅度调整
    protected float ratioAspect = 0.68f; // (0,1]
    protected float animationSpeed = 0.1f; // (0,1]

    private int state;
    private int lastState;
    private boolean isCanVisibleDrawing = false;
    private OnClickListener mOnClickListener;
    //开启状态背景色
    protected int colorPrimary;
    //开启状态按钮描边色
    protected int colorPrimaryDark;
    //关闭状态描边色
    protected int colorOff;
    //关闭状态按钮描边色
    protected int colorOffDark;
    //按钮阴影色
    protected int colorShadow;
    protected int colorBar;
    protected int colorBackground;
    protected boolean hasShadow;
    protected boolean isOpened;

    private float sRight;
    private float sCenterX, sCenterY;
    private float sScale;

    private float bOffset;
    private float bRadius, bStrokeWidth;
    private float bWidth;
    private float bLeft;
    private float bRight;
    private float bOnLeftX, bOn2LeftX, bOff2LeftX, bOffLeftX;

    private float shadowReservedHeight;
    //里面小圆圈 相对于外面椭圆的距离；
    private float offsetBtoS;
    private boolean canTouch = true;

    public SwitchView(Context context) {
        this(context, null);
    }

    public void setCanTouchSwitch(boolean canTouch) {
        this.canTouch = canTouch;
    }
    public boolean isCanTouch(){
        return canTouch ;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        final int DEFAULT_COLOR_PRIMARY = 0xFFFF6400;
        final int DEFAULT_COLOR_PRIMARY_DARK = 0xFFFFFFFF;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
        colorPrimary = a.getColor(R.styleable.SwitchView_primaryColor, DEFAULT_COLOR_PRIMARY);
        colorPrimaryDark = a.getColor(R.styleable.SwitchView_primaryColorDark, DEFAULT_COLOR_PRIMARY_DARK);
        colorOff = a.getColor(R.styleable.SwitchView_offColor, 0xFFEEEEEE);
        colorOffDark = a.getColor(R.styleable.SwitchView_offColorDark, 0x00000000);
        colorShadow = a.getColor(R.styleable.SwitchView_shadowColor, 0xFF333333);
        colorBar = a.getColor(R.styleable.SwitchView_barColor, 0xFFFFFFFF);
        colorBackground = a.getColor(R.styleable.SwitchView_bgColor, 0xFFEEEEEE);
        ratioAspect = a.getFloat(R.styleable.SwitchView_ratioAspect, 0.68f);
        hasShadow = a.getBoolean(R.styleable.SwitchView_hasShadow, true);
        isOpened = a.getBoolean(R.styleable.SwitchView_isOpened, false);
        state = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        lastState = state;
        offsetBtoS = a.getFloat(R.styleable.SwitchView_offsetBtoS, 2);
        a.recycle();

        if (colorPrimary == DEFAULT_COLOR_PRIMARY && colorPrimaryDark == DEFAULT_COLOR_PRIMARY_DARK) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TypedValue primaryColorTypedValue = new TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.colorPrimary, primaryColorTypedValue, true);
                    if (primaryColorTypedValue.data > 0) colorPrimary = primaryColorTypedValue.data;
                    TypedValue primaryColorDarkTypedValue = new TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, primaryColorDarkTypedValue, true);
                    if (primaryColorDarkTypedValue.data > 0)
                        colorPrimaryDark = primaryColorDarkTypedValue.data;
                }
            } catch (Exception ignore) {
            }
        }
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark) {
        setColor(newColorPrimary, newColorPrimaryDark, colorOff, colorOffDark);
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark) {
        setColor(newColorPrimary, newColorPrimaryDark, newColorOff, newColorOffDark, colorShadow);
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark, int newColorShadow) {
        colorPrimary = newColorPrimary;
        colorPrimaryDark = newColorPrimaryDark;
        colorOff = newColorOff;
        colorOffDark = newColorOffDark;
        colorShadow = newColorShadow;
        invalidate();
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark, int newColorShadow, int newColorBar, int newColorBackground) {
        colorPrimary = newColorPrimary;
        colorPrimaryDark = newColorPrimaryDark;
        colorOff = newColorOff;
        colorOffDark = newColorOffDark;
        colorShadow = newColorShadow;
        colorBar = newColorBar;
        colorBackground = newColorBackground;
        invalidate();
    }

    public void setShadow(boolean shadow) {
        hasShadow = shadow;
        invalidate();
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean isOpened) {
        int wishState = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        if (wishState == state) {
            return;
        }
        refreshState(wishState);
    }

    public void toggleSwitch(boolean isOpened) {
        int wishState = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        if (wishState == state) {
            return;
        }
        if ((wishState == STATE_SWITCH_ON && (state == STATE_SWITCH_OFF || state == STATE_SWITCH_OFF2))
                || (wishState == STATE_SWITCH_OFF && (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2))) {
            sAnim = 1;
        }
        bAnim = 1;
        refreshState(wishState);
    }

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int resultWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = widthSize;
        } else {
            resultWidth = (int) (56 * getResources().getDisplayMetrics().density + 0.5f)
                    + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, widthSize);
            }
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int resultHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = heightSize;
        } else {
            resultHeight = (int) (resultWidth * ratioAspect) + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, heightSize);
            }
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        isCanVisibleDrawing = w > getPaddingLeft() + getPaddingRight() && h > getPaddingTop() + getPaddingBottom();

        if (isCanVisibleDrawing) {
            int actuallyDrawingAreaWidth = w - getPaddingLeft() - getPaddingRight();
            int actuallyDrawingAreaHeight = h - getPaddingTop() - getPaddingBottom();

            int actuallyDrawingAreaLeft;
            int actuallyDrawingAreaRight;
            int actuallyDrawingAreaTop;
            int actuallyDrawingAreaBottom;
            if (actuallyDrawingAreaWidth * ratioAspect < actuallyDrawingAreaHeight) {
                actuallyDrawingAreaLeft = getPaddingLeft();
                actuallyDrawingAreaRight = w - getPaddingRight();
                int heightExtraSize = (int) (actuallyDrawingAreaHeight - actuallyDrawingAreaWidth * ratioAspect);
                actuallyDrawingAreaTop = getPaddingTop() + heightExtraSize / 2;
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom() - heightExtraSize / 2;
            } else {
                int widthExtraSize = (int) (actuallyDrawingAreaWidth - actuallyDrawingAreaHeight / ratioAspect);
                actuallyDrawingAreaLeft = getPaddingLeft() + widthExtraSize / 2;
                actuallyDrawingAreaRight = getWidth() - getPaddingRight() - widthExtraSize / 2;
                actuallyDrawingAreaTop = getPaddingTop();
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom();
            }

            shadowReservedHeight = (int) ((actuallyDrawingAreaBottom - actuallyDrawingAreaTop) * 0.07f);
            float sLeft = actuallyDrawingAreaLeft;
            float sTop = actuallyDrawingAreaTop + shadowReservedHeight;
            sRight = actuallyDrawingAreaRight;
            float sBottom = actuallyDrawingAreaBottom - shadowReservedHeight;

            float sHeight = sBottom - sTop;
            sCenterX = (sRight + sLeft) / 2;
            sCenterY = (sBottom + sTop) / 2;

            bLeft = sLeft;
            bWidth = sBottom - sTop;
            bRight = sLeft + bWidth;
            final float halfHeightOfS = bWidth / 2; // OfB
            bRadius = halfHeightOfS * 0.95f;
            bOffset = bRadius * 0.2f; // offset of switching
            bStrokeWidth = (halfHeightOfS - bRadius) * 2;
            bOnLeftX = sRight - bWidth;
            bOn2LeftX = bOnLeftX - bOffset;
            bOffLeftX = sLeft;
            bOff2LeftX = bOffLeftX + bOffset;
            sScale = 1 - bStrokeWidth / sHeight;

            sPath.reset();
            //椭圆
            RectF sRectF = new RectF();
            sRectF.top = sTop;
            sRectF.bottom = sBottom;
            sRectF.left = sLeft;
            sRectF.right = sLeft + sHeight;
            sPath.arcTo(sRectF, 90, 180);
            sRectF.left = sRight - sHeight;
            sRectF.right = sRight;
            sPath.arcTo(sRectF, 270, 180);
            sPath.close();

            //圆
            bRectF.left = bLeft + offsetBtoS;
            bRectF.right = bRight - offsetBtoS;
            bRectF.top = sTop + bStrokeWidth / 2 + offsetBtoS;  // bTop = sTop
            bRectF.bottom = sBottom - bStrokeWidth / 2 - offsetBtoS; // bBottom = sBottom
            float bCenterX = (bRight + bLeft) / 2;
            float bCenterY = (sBottom + sTop) / 2;
            shadowGradient = new RadialGradient(bCenterX, bCenterY, bRadius, Color.parseColor("#26000000"),
                    Color.parseColor("#26000000"), Shader.TileMode.CLAMP);
        }
    }

    /**
     * @Description 画里面的圆圈⭕️
     * @author WinterSweett
     */
    private void calcBPath(float percent) {
        Log.d("ws", "calcBPath: percent:" + percent + "  " + bOffset);
        bPath.reset();
        bRectF.left = bLeft + bStrokeWidth / 2 + offsetBtoS;
        bRectF.right = bRight - bStrokeWidth / 2 - offsetBtoS;
        Log.d("ws", "calcBPath: " + bRectF.left + "  " + bRectF.right + "  " + bLeft + "  " + bRight + "  " + bStrokeWidth);
        bPath.arcTo(bRectF, 90, 180);
        bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2 + offsetBtoS;
        bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2 - offsetBtoS;
        bPath.arcTo(bRectF, 270, 180);
        bPath.close();
    }

    private float calcBTranslate(float percent) {
        float result = 0;
        switch (state - lastState) {
            case 1:
                if (state == STATE_SWITCH_OFF2) {
                    result = bOffLeftX; // off -> off2
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOn2LeftX) * percent; // on2 -> on
                }
                break;
            case 2:
                if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off2 -> on
                } else if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX - (bOn2LeftX - bOffLeftX) * percent;  // off -> on2
                }
                break;
            case 3:
                result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off -> on
                break;
            case -1:
                if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX + (bOnLeftX - bOn2LeftX) * percent; // on -> on2
                } else if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX;  // off2 -> off
                }
                break;
            case -2:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX + (bOn2LeftX - bOffLeftX) * percent;  // on2 -> off
                } else if (state == STATE_SWITCH_OFF2) {
                    result = bOff2LeftX + (bOnLeftX - bOff2LeftX) * percent;  // on -> off2
                }
                break;
            case -3:
                result = bOffLeftX + (bOnLeftX - bOffLeftX) * percent;  // on -> off
                break;
            default: // init
            case 0:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX; //  off -> off
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX; // on -> on
                }
                break;
        }
        return result - bOffLeftX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isCanVisibleDrawing) return;

        paint.setAntiAlias(true);
        final boolean isOn = (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2);
        // Draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(isOn ? colorPrimary : colorOff);
        //sPath 画的打开情况下的：背景椭圆
        canvas.drawPath(sPath, paint);

        sAnim = sAnim - animationSpeed > 0 ? sAnim - animationSpeed : 0;
        bAnim = bAnim - animationSpeed > 0 ? bAnim - animationSpeed : 0;

        final float dsAnim = interpolator.getInterpolation(sAnim);
        final float dbAnim = interpolator.getInterpolation(bAnim);
        // Draw background animation
        final float scale = sScale * (isOn ? dsAnim : 1 - dsAnim);
        final float scaleOffset = (sRight - sCenterX - bRadius) * (isOn ? 1 - dsAnim : dsAnim);
        canvas.save();
        canvas.scale(scale, scale, sCenterX + scaleOffset, sCenterY);
        paint.setColor(colorBackground);
        //此时画的 关闭情况下的 背景椭圆
        canvas.drawPath(sPath, paint);
        canvas.restore();
        // To prepare center bar path
        canvas.save();
        canvas.translate(calcBTranslate(dbAnim), shadowReservedHeight);
        final boolean isState2 = (state == STATE_SWITCH_ON2 || state == STATE_SWITCH_OFF2);
        calcBPath(isState2 ? 1 - dbAnim : dbAnim);
        // Use center bar path to draw shadow
        if (hasShadow) {
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(bRadius, 0, 3, Color.parseColor("#0F000000"));
            paint.setShader(shadowGradient);
            //paint.setShader(sweepGradient) ;
            canvas.drawPath(bPath, paint);
            paint.setShader(null);
        }
        canvas.translate(0, -shadowReservedHeight);
        // draw bar
        canvas.scale(0.98f, 0.98f, bWidth / 2, bWidth / 2);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorBar);
        //bPath 此时画的 圆圈按钮⭕️
        canvas.drawPath(bPath, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bStrokeWidth * 0.5f);
        paint.setColor(isOn ? colorPrimaryDark : colorOffDark);
        //bPath 此时画的 圆圈⭕️的外边框
        canvas.drawPath(bPath, paint);
        canvas.restore();

        paint.reset();
        if (sAnim > 0 || bAnim > 0) invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((state == STATE_SWITCH_ON || state == STATE_SWITCH_OFF) && (sAnim * bAnim == 0)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_UP:
                    if (!canTouch) {
                        break;
                    }
                    lastState = state;

                    bAnim = 1;
                    if (state == STATE_SWITCH_OFF) {
                        refreshState(STATE_SWITCH_OFF2);
                        listener.toggleToOn(this);
                    } else if (state == STATE_SWITCH_ON) {
                        refreshState(STATE_SWITCH_ON2);
                        listener.toggleToOff(this);
                    }

                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    public interface OnStateChangedListener {
        void toggleToOn(SwitchView view);

        void toggleToOff(SwitchView view);
    }

    private OnStateChangedListener listener = new OnStateChangedListener() {
        @Override
        public void toggleToOn(SwitchView view) {
            toggleSwitch(true);
        }

        @Override
        public void toggleToOff(SwitchView view) {
            toggleSwitch(false);
        }
    };

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        if (listener == null) throw new IllegalArgumentException("empty listener");
        this.listener = listener;
    }


}
