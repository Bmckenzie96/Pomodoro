package com.a1996.ben.pomodoro.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class SplashScreen extends AppCompatActivity {

    ProgressBar mProgressBar;
    Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgressBar = (ProgressBar) findViewById(R.id.splashProgressBar);
        mRetry = (Button) findViewById(R.id.retrySplash);
        String appVersion = "v1";
        Backendless.initApp( this, "2B14DE22-099E-0107-FF72-32ACB6AB4400", "FC429597-C101-BEB9-FF19-344F9FCB9200", appVersion );
        final AsyncCallback<Boolean> isValidLoginCallback = new AsyncCallback<Boolean>()
        {
            @Override
            public void handleResponse( Boolean response )
            {
                mProgressBar.setVisibility(View.INVISIBLE);
                System.out.println( "[ASYNC] Is login valid? - " + response );
                if(response == true) {
                    Intent intent = new Intent(SplashScreen.this, SignInActivity.class);
                    intent.putExtra("GOTOHOME", true);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, SignInActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                mProgressBar.setVisibility(View.INVISIBLE);
                System.err.println( "Error - " + fault );
                Toast.makeText(SplashScreen.this, "Make sure internet connection is on", Toast.LENGTH_SHORT).show();
                mRetry.setVisibility(View.VISIBLE);
            }

        };
        attemptLogin(isValidLoginCallback);
        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(isValidLoginCallback);
                mRetry.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void attemptLogin(AsyncCallback<Boolean> isValidLogin) {
        mProgressBar.setVisibility(View.VISIBLE);
        Backendless.UserService.isValidLogin(isValidLogin);
    }
}
