package com.example.night.neteasemusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.night.neteasemusic.R;

/**
 * Created by Night on 2017/10/14.
 * Desc:
 */

public class SideBar extends View {
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();
    private TextView mTextDialog;

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.search_indexbar_bg);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SideBar(Context context) {
        super(context);

    }

    public void setView(TextView textDialog) {
        this.mTextDialog = textDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.removeCallbacks(mRunnable);

        int w = getWidth();
        int h = getHeight();
        int singleHeight = h / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            if (i == choose) {
                paint.setColor(Color.WHITE);
                paint.setFakeBoldText(true);
            }
            float x = w / 2 - paint.measureText(b[i]) / 2;
            float y = singleHeight * i + singleHeight;
            canvas.drawText(b[i], x, y, paint);
            paint.reset();
        }
        this.postDelayed(mRunnable, 2000);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            SideBar.this.setVisibility(INVISIBLE);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchLetterListener listener = onTouchLetterListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(INVISIBLE);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                this.removeCallbacks(mRunnable);
                SideBar.this.setVisibility(VISIBLE);
                break;

            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;

        }
        return true;
    }

    private OnTouchLetterListener onTouchLetterListener;

    public void setOnTouchLetterListener(OnTouchLetterListener onTouchLetterChangedListener) {
        this.onTouchLetterListener = onTouchLetterChangedListener;
    }

    public interface OnTouchLetterListener {
        void onTouchLetterChanged(String s);
    }

    /**
     * 设置当前字符
     *
     * @param nowChar
     */
    public void setSelected(String nowChar) {
        Log.i("OnRecyclerViewOnScrol", "setSelected:" + nowChar);
        if (nowChar != null) {
            for (int i = 0; i < b.length; i++) {
                if (b[i].equals(nowChar)) {
                    choose = i;
                    break;
                }
                if (i == b.length - 1) {
                    choose = -1;
                }
            }
            invalidate();//刷新整个view
        }
    }
}
