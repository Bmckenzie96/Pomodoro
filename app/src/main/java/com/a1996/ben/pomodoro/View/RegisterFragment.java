package com.a1996.ben.pomodoro.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a1996.ben.pomodoro.R;

/**
 * Created by Ben on 6/14/2016.
 */
public class RegisterFragment extends Fragment {
    public interface registerInterface {
        public void hideButton(Button button);
        public void hidePasswordMatch(TextView textView);
        public void showButton(Button button);
        public void showPasswordMatch(TextView textView);
        public void registerUser(Button button, ProgressBar progressBar, EditText email, EditText password);
    }
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    Button mRegButton;
    ProgressBar mRegProgressBar;
    TextView mPasswordMatch;
    registerInterface mRegisterInterface;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        mEmail = (EditText) view.findViewById(R.id.regEmail);
        mPassword = (EditText) view.findViewById(R.id.regPassword);
        mConfirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        mRegButton = (Button) view.findViewById(R.id.regButton);
        mRegProgressBar = (ProgressBar) view.findViewById(R.id.regProgress);
        mPasswordMatch = (TextView) view.findViewById(R.id.passwordMatch);
        mRegisterInterface = (registerInterface) getActivity();
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterInterface.registerUser(mRegButton, mRegProgressBar, mEmail, mPassword);
            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                    mRegisterInterface.hideButton(mRegButton);
                    mRegisterInterface.showPasswordMatch(mPasswordMatch);
                }
                else {
                    mRegisterInterface.showButton(mRegButton);
                    mRegisterInterface.hidePasswordMatch(mPasswordMatch);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                    mRegisterInterface.hideButton(mRegButton);
                    mRegisterInterface.showPasswordMatch(mPasswordMatch);
                }
                else {
                    mRegisterInterface.showButton(mRegButton);
                    mRegisterInterface.hidePasswordMatch(mPasswordMatch);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}
