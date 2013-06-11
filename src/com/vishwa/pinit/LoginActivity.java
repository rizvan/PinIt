/*
 * Copyright 2013 Vishwa Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in the 'assets' directory of this 
 * application or at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vishwa.pinit;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.andreabaccega.widget.FormEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    private Button mLoginButton;
    private Button mSignupButton;
    private FormEditText mUsernameField;
    private FormEditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_login);

        setProgressBarIndeterminateVisibility(false);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mSignupButton = (Button) findViewById(R.id.signup_button);
        mUsernameField = (FormEditText) findViewById(R.id.login_username_field);
        mPasswordField = (FormEditText) findViewById(R.id.login_password_field);

        resizeButtons();

        mLoginButton.setOnClickListener(new LoginClickListener());

        mSignupButton.setOnClickListener(new SignupClickListener());
    }

    public class LoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(PinItUtils.isOnline(getApplicationContext())) {
                FormEditText[] allFields = { mUsernameField, mPasswordField };

                boolean allValid = true;
                for(FormEditText field: allFields) {
                    allValid = field.testValidity() && allValid;
                }

                if(allValid) {
                    String username = mUsernameField.getText().toString().trim();
                    String password = mPasswordField.getText().toString();

                    setProgressBarIndeterminateVisibility(true);

                    ParseUser.logInInBackground(username, password, new LogInCallback() {

                        @Override
                        public void done(ParseUser user, ParseException e) {

                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                String error = e.getMessage().substring(0, 1).toUpperCase() + 
                                        e.getMessage().substring(1);

                                PinItUtils.createAlert("Unable to login", error, LoginActivity.this);
                            }
                        }
                    });
                }
            }
            else {
                PinItUtils.createAlert("Internet connection not found.", "This app "+
                        "needs an active Internet connection!", LoginActivity.this);
            }
        }

    }

    public class SignupClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent signupIntent = new Intent(getApplicationContext(),
                    SignupActivity.class);
            startActivity(signupIntent);
        }
    }

    public void resizeButtons() {

        ViewGroup.LayoutParams signupparams = mSignupButton.getLayoutParams();
        ViewGroup.LayoutParams loginparams = mLoginButton.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        signupparams.width = (int) width / 2;
        loginparams.width = (int) ((width*3)/5);

        mSignupButton.setLayoutParams(signupparams);
        mLoginButton.setLayoutParams(loginparams);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

