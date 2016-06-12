package Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;
import com.a1996.ben.pomodoro.View.AddTask;
import com.a1996.ben.pomodoro.View.Home;
import com.a1996.ben.pomodoro.View.TimerFragment;

/**
 * Created by Ben on 6/4/2016.
 */
public class CountdownTimer {
    private static CountdownTimer mInstance;
    private static boolean justStarted;
    public static boolean isFocus;
    public static long currentSeconds;
    public static boolean isCounting;
    private CountDownTimer mCountDownTimer;
    private CountdownTimer() {}
    private static TextView mTimeView;
    private static Context mContext;
    public static CountdownTimer getInstance(TextView textView, Context context) {
        if (mInstance == null) {
            mInstance = new CountdownTimer();
            mInstance.isFocus = true;
            mInstance.justStarted = true;
            mInstance.isCounting = false;
            mTimeView = textView;
            mContext = context.getApplicationContext();
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
            startTime(getInstance(mTimeView, mContext).currentSeconds, 1000, textView);
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
                if (seconds < 10) {
                    textView.setText(minutes + ":0" + seconds);
                }
                else {
                    textView.setText(minutes + ":" + seconds);
                }
                Log.i("seconds remaining", millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {
                textView.setText("0");
                if (mInstance.isFocus) {
                    notifyTimeUp("Done Focusing!", "Your time for focus is up, your break started!");
                    startTime(300, 1000, mTimeView);
                    mInstance.isFocus = false;
                }
                else {
                    notifyTimeUp("Done with Break!", "All good things come to an end, get back to work!");
                    startTime(1500, 1000, mTimeView);
                    mInstance.isFocus = true;
                }
            }
        }.start();
    }

    public void notifyTimeUp(String title, String content) {
        if (Home.isRunning) {
            return;
        }
        TimerFragment.fromNotification = true;
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, Home.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(Home.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(5, mBuilder.build());
    }
}
