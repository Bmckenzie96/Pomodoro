package Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

/**
 * Created by Ben on 6/4/2016.
 */
public class CountdownTimer {
    private static CountdownTimer mInstance;
    private static boolean justStarted;
    private static boolean isFocus;
    public static long currentSeconds;
    public static boolean isCounting;
    private CountDownTimer mCountDownTimer;
    private CountdownTimer() {}
    public static CountdownTimer getInstance() {
        if (mInstance == null) {
            mInstance = new CountdownTimer();
            mInstance.isFocus = true;
            mInstance.justStarted = true;
            mInstance.isCounting = false;
        }
        return mInstance;
    }
    public void pause() {
        if (mInstance.mCountDownTimer != null) {
            mInstance.mCountDownTimer.cancel();
        }
        mInstance.isCounting = false;
    }
    public void play(TextView textView) {
        if (mInstance.justStarted) {
            startTime(1500, 1000, textView);
        }
        else {
            startTime(getInstance().currentSeconds, 1000, textView);
        }
    }
    public void startTime(long time, long delay, final TextView textView) {
        mInstance.justStarted = false;
        if(mInstance.mCountDownTimer != null) {
            mInstance.mCountDownTimer.cancel();
        }
        mInstance.isCounting = true;
        mInstance.mCountDownTimer = new CountDownTimer(time * 1000 + 100, delay) { //+100 to account for computation time
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished/1000)/60;
                long seconds = (millisUntilFinished/1000)%60;
                mInstance.currentSeconds = millisUntilFinished/1000;
                if (seconds == 0) {
                    textView.setText(minutes + ":00");
                }
                else {
                    textView.setText(minutes + ":" + seconds);
                }
                Log.i("seconds remaining", millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {
                textView.setText("0");
            }
        }.start();
    }
}
