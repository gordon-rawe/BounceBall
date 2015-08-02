package rawe.gordon.com.boucingballdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by gordon on 2015/8/1.
 */
public class BottomDetectScrollView extends ScrollView {
    private OnScrollListener listener;

    public BottomDetectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (listener != null) {
            listener.onScrollChanged(l, t, oldl, oldt);
            if ((getChildAt(getChildCount() - 1).getBottom() - (getHeight() + getScrollY())) == 0) {
                listener.onBottomReached();
            }
        }
    }


    float downY = 0f;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (ev.getRawY() > downY) {
                    if (listener != null) listener.onScrolledUp();
                } else {
                    if (listener != null) listener.onScrolledDown();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public interface OnScrollListener {
        void onScrolledDown();

        void onScrolledUp();

        void onBottomReached();

        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
