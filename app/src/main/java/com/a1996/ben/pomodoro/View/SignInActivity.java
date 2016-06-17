package com.a1996.ben.pomodoro.View;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a1996.ben.pomodoro.R;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.backendless.persistence.local.UserTokenStorageFactory;

import Utils.TaskSQLHelper;

public class SignInActivity extends AppCompatActivity implements SigninFragment.signInInterface,
RegisterFragment.registerInterface{

    boolean isValidLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        String appVersion = "v1";
        Backendless.initApp( this, "2B14DE22-099E-0107-FF72-32ACB6AB4400", "FC429597-C101-BEB9-FF19-344F9FCB9200", appVersion );
        AsyncCallback<Boolean> isValidLoginCallback = new AsyncCallback<Boolean>()
        {
            @Override
            public void handleResponse( Boolean response )
            {
                System.out.println( "[ASYNC] Is login valid? - " + response );
                if(response == true) {
                    Intent intent = new Intent(SignInActivity.this, Home.class);
                    startActivity(intent);
                }
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
                TaskSQLHelper.BackendlessUserId = Backendless.UserService.CurrentUser().getUserId();
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

    @Override
    public void register() {
        RegisterFragment registerFragment = new RegisterFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.signInOrRegisterPlaceholder, registerFragment)
                .addToBackStack("Register").commit();
    }

    @Override
    public void hideButton(Button button) {
        button.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hidePasswordMatch(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showButton(Button button) {
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPasswordMatch(TextView textView) {
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void registerUser(final Button button, final ProgressBar progressBar, EditText email, EditText password) {
        final BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", email.getText().toString() );
        user.setPassword( password.getText().toString() );
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        try {
            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                public void handleResponse(BackendlessUser registeredUser) {
                    // user has been registered and now can login
                    TaskSQLHelper.BackendlessUserId = user.getUserId();
                    Log.i("user id", TaskSQLHelper.BackendlessUserId);
                    getFragmentManager().popBackStack();
                    Intent intent = new Intent(SignInActivity.this, Home.class);
                    startActivity(intent);

                }

                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    Toast.makeText(SignInActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                    button.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } catch ( java.lang.IllegalArgumentException ex) {
            Toast.makeText(SignInActivity.this, "Email and Password cannot be empty", Toast.LENGTH_LONG).show();
            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


}
