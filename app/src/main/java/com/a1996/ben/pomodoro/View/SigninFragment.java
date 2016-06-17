package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

import org.w3c.dom.Text;

/**
 * Created by Ben on 6/13/2016.
 */
public class SigninFragment extends Fragment {
    public interface signInInterface {
        public void signIn(EditText email, EditText password, ProgressBar progressBar, Button button);
        public void register();
    }
    EditText mEmail;
    EditText mPassword;
    ProgressBar mProgressBar;
    TextView mRegisterTextView;
    Button mSigninButton;
    signInInterface mSignInInterface;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        mEmail = (EditText) view.findViewById(R.id.loginEmail);
        mPassword = (EditText) view.findViewById(R.id.loginPassword);
        mProgressBar = (ProgressBar) view.findViewById(R.id.loginProgress);
        mSigninButton = (Button) view.findViewById(R.id.signinButton);
        mSignInInterface = (signInInterface) getActivity();
        mRegisterTextView = (TextView) view.findViewById(R.id.registerTextView);
        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInInterface.signIn(mEmail, mPassword, mProgressBar, mSigninButton);
            }
        });
        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInInterface.register();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mEmail.setText("");
        mPassword.setText("");
    }
}
