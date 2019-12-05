package com.nieroda.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.nieroda.app.R;
import com.nieroda.app.utils.ValidationUtil;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.email_input)
    TextInputLayout emailInput;
    @BindView(R.id.password_input)
    TextInputLayout passwordInput;
    @BindView(R.id.logo)
    ImageView logo;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    void onLoginClick() {
        String email = emailInput.getEditText().getText().toString().trim().toLowerCase();
        String password = passwordInput.getEditText().getText().toString().trim();
        if (ValidationUtil.validate(emailInput, passwordInput, email, password)) {
            MainActivity.launch(this, logo, email, password);
        }
    }



    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.prompt_tap_twice_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }

}
