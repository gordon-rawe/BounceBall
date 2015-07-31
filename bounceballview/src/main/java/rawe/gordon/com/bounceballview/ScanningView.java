package rawe.gordon.com.bounceballview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


/**
 * Created by gordon on 7/29/15.
 */
public class ScanningView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean exitFlag = true;
    private Canvas canvas;
    private float angle = 0;
    private int containerWidth = 600;//�������һ��ֵ
    private Path path;
    private int center_x, center_y;
    private float sqrt3 = 1.7320508f;
    private int borderWidth;
    private float outterRadius = 0f;

    //coordinates
    private ArrayList<PointF> triangle1 = new ArrayList<>();
    private ArrayList<PointF> triangle2 = new ArrayList<>();
    private ArrayList<PointF> triangle3 = new ArrayList<>();
    private ArrayList<PointF> triangle4 = new ArrayList<>();

    {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
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
        center_x = getWidth() / 2;
        center_y = getHeight() / 2;
        borderWidth = containerWidth / 40;
        outterRadius = containerWidth/2-borderWidth-10;
        calcCoordinates();
        new Thread(this).start();
    }

    private void calcCoordinates() {
        //calc triangles' coordinates
        triangle1.add(new PointF(((float) (containerWidth / 2.0 - borderWidth * sqrt3 / 3)), 0f));
        triangle1.add(new PointF(((float) (containerWidth / 2.0 + borderWidth * sqrt3 / 3)), 0));
        triangle1.add(new PointF(((float) (containerWidth / 2.0)), borderWidth));

        triangle2.add(new PointF(((float) (containerWidth / 2.0 - borderWidth * sqrt3 / 3)), containerWidth));
        triangle2.add(new PointF(((float) (containerWidth / 2.0 + borderWidth * sqrt3 / 3)), containerWidth));
        triangle2.add(new PointF(((float) (containerWidth / 2.0)), containerWidth - borderWidth));

        triangle3.add(new PointF(0, ((float) (containerWidth / 2.0 - borderWidth * sqrt3 / 3))));
        triangle3.add(new PointF(0, ((float) (containerWidth / 2.0 + borderWidth * sqrt3 / 3))));
        triangle3.add(new PointF(borderWidth, ((float) (containerWidth / 2.0))));

        triangle4.add(new PointF(containerWidth, ((float) (containerWidth / 2.0 - borderWidth * sqrt3 / 3))));
        triangle4.add(new PointF(containerWidth, ((float) (containerWidth / 2.0 + borderWidth * sqrt3 / 3))));
        triangle4.add(new PointF(containerWidth - borderWidth, ((float) (containerWidth / 2.0))));
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
        try {

            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


            //draw four borders first
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#cc0099ff"));
            //four corners
            path.moveTo(containerWidth / 3, borderWidth / 2);
            path.lineTo(borderWidth / 2, borderWidth / 2);
            path.lineTo(borderWidth / 2, containerWidth / 3);
            canvas.drawPath(path, paint);
            path.reset();
            path.moveTo(containerWidth * 2 / 3, borderWidth / 2);
            path.lineTo(containerWidth - borderWidth / 2, borderWidth / 2);
            path.lineTo(containerWidth - borderWidth / 2, containerWidth / 3);
            canvas.drawPath(path, paint);
            path.reset();
            path.moveTo(containerWidth / 3, containerWidth - borderWidth / 2);
            path.lineTo(borderWidth / 2, containerWidth - borderWidth / 2);
            path.lineTo(borderWidth / 2, containerWidth * 2 / 3);
            canvas.drawPath(path, paint);
            path.reset();
            path.moveTo(containerWidth * 2 / 3, containerWidth - borderWidth / 2);
            path.lineTo(containerWidth - borderWidth / 2, containerWidth - borderWidth / 2);
            path.lineTo(containerWidth - borderWidth / 2, containerWidth * 2 / 3);
            canvas.drawPath(path, paint);
            //four triangles
            paint.reset();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#cc0099ff"));
            //four triangles
            path.reset();
            path.moveTo(triangle1.get(0).x, triangle1.get(0).y);
            path.lineTo(triangle1.get(1).x, triangle1.get(1).y);
            path.lineTo(triangle1.get(2).x, triangle1.get(2).y);
            path.close();
            canvas.drawPath(path, paint);

//            path.reset();
            path.moveTo(triangle2.get(0).x, triangle2.get(0).y);
            path.lineTo(triangle2.get(1).x, triangle2.get(1).y);
            path.lineTo(triangle2.get(2).x, triangle2.get(2).y);
            path.close();
            canvas.drawPath(path, paint);

//            path.reset();
            path.moveTo(triangle3.get(0).x, triangle3.get(0).y);
            path.lineTo(triangle3.get(1).x, triangle3.get(1).y);
            path.lineTo(triangle3.get(2).x, triangle3.get(2).y);
            path.close();
            canvas.drawPath(path, paint);

//            path.reset();
            path.moveTo(triangle4.get(0).x, triangle4.get(0).y);
            path.lineTo(triangle4.get(1).x, triangle4.get(1).y);
            path.lineTo(triangle4.get(2).x, triangle4.get(2).y);
            path.close();
            canvas.drawPath(path, paint);

            //draw outer ring
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderWidth*0.7f);
            RectF outterRing = new RectF(center_x-outterRadius,center_y-outterRadius,center_x+outterRadius,center_y+outterRadius);
            canvas.drawArc(outterRing,10+angle,70,false,paint);
            canvas.drawArc(outterRing,100+angle,70,false,paint);
            canvas.drawArc(outterRing,190+angle,70,false,paint);
            canvas.drawArc(outterRing,280+angle,70,false,paint);
            angle++;
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
