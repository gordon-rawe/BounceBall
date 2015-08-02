package rawe.gordon.com.boucingballdemo;

import android.os.Handler;

/**
 * Created by gordon on 2015/8/1.
 */
public class JobDriver {
    private Runnable runnable;
    private long interval;
    private long tickTime;
    private Handler handler;
    private JobInterface jobInterface;
    {
        tickTime = 0;
        interval = 1000;
        handler = new Handler();
    }

    public JobDriver setJob(final JobInterface job, long intervalTime){
        this.interval = intervalTime;
        this.jobInterface = job;
        runnable = new Runnable() {
            @Override
            public void run() {
                if(jobInterface!=null) jobInterface.nextTick(tickTime);
                tickTime++;
                handler.postDelayed(this, interval);
            }
        };
        return this;
    }

    public JobDriver startJobInMilliSec(long millis){
        handler.postDelayed(runnable,millis);
        return this;
    }

    public JobDriver startJobNow(){
        handler.post(runnable);
        return this;
    }

    public JobDriver stopJob(){
        handler.removeCallbacks(runnable);
        return this;
    }

    public interface JobInterface{
        void nextTick(long tickTime);
    }
}