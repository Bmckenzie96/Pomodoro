package com.a1996.ben.pomodoro.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;

public class SignInActivity extends AppCompatActivity implements SigninFragment.signInInterface{

    boolean isValidLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        String appVersion = "v1";
        Backendless.initApp( this, "2B14DE22-099E-0107-FF72-32ACB6AB4400", "FC429597-C101-BEB9-FF19-344F9FCB9200", appVersion );
        // UserTokenStorageFactory is available in the com.backendless.persistence.local package
        AsyncCallback<Boolean> isValidLoginCallback = new AsyncCallback<Boolean>()
        {
            @Override
            public void handleResponse( Boolean response )
            {
                System.out.println( "[ASYNC] Is login valid? - " + response );
                Intent intent = new Intent(SignInActivity.this, Home.class);
                startActivity(intent);
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                System.err.println( "Error - " + fault );
            }

        };

        Backendless.UserService.isValidLogin( isValidLoginCallback );
        SigninFragment signinFragment = new SigninFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.signInOrRegisterPlaceholder, signinFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void signIn(EditText email, EditText password, final ProgressBar progressBar, final Button button) {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        Backendless.UserService.login( email.getText().toString(),
                password.getText().toString(), new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser user )
            {
                // user has been logged in
                Intent intent = new Intent(SignInActivity.this, Home.class);
                startActivity(intent);
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
            }

            public void handleFault( BackendlessFault fault )
            {
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                // login failed, to get the error code call fault.getCode()
                Toast.makeText(SignInActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }
}
