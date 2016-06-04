package Utils;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Ben on 6/4/2016.
 */
public class CountdownTimer {
    private static CountdownTimer mInstance;
    private static boolean isFocus;
    private static long currentSeconds;
    private static boolean isPlay;
    private CountDownTimer mCountDownTimer;
    private CountdownTimer() {}
    public static CountdownTimer getInstance() {
        if (mInstance == null) {
            mInstance = new CountdownTimer();
            mInstance.isFocus = true;
            mInstance.currentSeconds = 0;
            mInstance.isPlay = true;
        }
        return mInstance;
    }
    public void startTime(long time, long delay, final TextView textView) {
        if(mInstance.mCountDownTimer != null) {
            mInstance.mCountDownTimer.cancel();
        }
        mInstance.mCountDownTimer = new CountDownTimer(time * 1000 + 100, delay) { //+100 to account for computation time
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished/1000)/60;
                long seconds = (millisUntilFinished/1000)%60;
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
