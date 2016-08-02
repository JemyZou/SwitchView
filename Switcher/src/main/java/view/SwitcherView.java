package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

import listener.OnSwitchListener;

/**
 * Created by Jemy on 2015/6/1.
 * 自定义开关view
 */
public class SwitcherView extends View {

    private Bitmap backgroundBitmap;
    private Bitmap switcherBitmap;
    private Paint paint;
    private boolean isOpened = false;//开关默认关闭
    private float downX;
    private int backgroundWidth;//底部开关图片资源的宽度
    private int switcherWidth;
    private static final int STATUS_INIT = 0;
    private static final int STATUS_DOWN = 1;
    private static final int STATUS_MOVE = 2;
    private static final int STATUS_UP = 3;
    private int status;
    private static final String TAG = "SwitcherView";
    private OnSwitchListener onSwitchListener;
    public SwitcherView(Context context) {
        this(context, null);
    }

    public SwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getReSourcesPicture(context);
    }

    /**
     * 获取背景和转换开关的图片资源,以便绘制
     */
    private void getReSourcesPicture(Context context) {
        paint = new Paint();
        backgroundBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.switch_background);
        switcherBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.slide_button_background);
        backgroundWidth = backgroundBitmap.getWidth();
        switcherWidth = switcherBitmap.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (backgroundBitmap != null) {
            int widthSpec = backgroundBitmap.getWidth();
            int heightSpec = backgroundBitmap.getHeight();
            setMeasuredDimension(widthSpec, heightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        }
        switch (status) {
            case STATUS_MOVE:
            case STATUS_DOWN://按下时开关布局随着手指移动
                    float left = downX - switcherWidth / 2f;
                    if (left < 0) {//限制左边界
                        left = 0;
                    }else if(downX>backgroundWidth-switcherWidth/2f){//限制右边界
                        left=backgroundWidth-switcherWidth;
                    }
                    canvas.drawBitmap(switcherBitmap, left, 0, paint);
                break;
            case STATUS_UP:
            case STATUS_INIT:
                if (switcherBitmap != null) {
                    if (!isOpened) {
                        canvas.drawBitmap(switcherBitmap, 0, 0, paint);
                    } else {
                        canvas.drawBitmap(switcherBitmap, backgroundWidth-switcherWidth, 0, paint);
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                status = STATUS_DOWN;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                status=STATUS_MOVE;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                status=STATUS_UP;
                downX=event.getX();
                if(downX<backgroundWidth/2f){
                    isOpened=false;
                    Log.i(TAG, "onTouchEvent: "+isOpened);
                }else{
                    isOpened=true;
                    Log.i(TAG, "onTouchEvent: "+isOpened);
                }
                if(onSwitchListener!=null){
                    onSwitchListener.onSwitch(isOpened);
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * @param onSwitchListener
     * 设置开关监听器
     */
    public void setOnSwitchListener(OnSwitchListener onSwitchListener){
        this.onSwitchListener=onSwitchListener;
    }
}
