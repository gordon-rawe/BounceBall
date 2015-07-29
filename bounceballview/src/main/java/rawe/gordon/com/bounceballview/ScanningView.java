package rawe.gordon.com.bounceballview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by gordon on 7/29/15.
 */
public class ScanningView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean exitFlag = true;
    private Canvas canvas;
    private float angle = 0;
    private int containerWidth = 600;
    private Path path;
    private int center_x,center_y;

    {
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.parseColor("#668800ff"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

    public ScanningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.containerWidth = getWidth() < getHeight() ? getWidth() : getHeight();
        center_x = getWidth()/2;
        center_y = getHeight()/2;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        exitFlag = false;
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tickWork() {
        //static area

        try {
            canvas = surfaceHolder.lockCanvas();
            canvas.save();
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//            canvas.drawRect(new RectF(0,0,getWidth(),getHeight()),paint);
            angle++;
            canvas.drawArc(new RectF(0,0,getWidth(),getHeight()),angle,120,true,paint);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (exitFlag) {
            tickWork();
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
