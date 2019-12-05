package com.nieroda.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nieroda.app.R;
import com.nieroda.networking.ApiResponseStatus;
import com.nieroda.networking.ApiService;
import com.nieroda.networking.HttpCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    private Call apiCall;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.success)
    TextView successText;

    public static void launch(final Activity context, View logo, final String email, final String password) {
        final Intent i = new Intent(context, MainActivity.class);
        Bundle b = new Bundle();
        b.putString(EMAIL_KEY, email);
        b.putString(PASSWORD_KEY, password);
        i.putExtras(b);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(context, logo, "logo");

        context.startActivity(i, options.toBundle());
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startLoginRequest();
    }

    private void startLoginRequest() {
        Bundle b = getIntent().getExtras();
        String email = b.getString(EMAIL_KEY);
        String password = b.getString(PASSWORD_KEY);
        apiCall = ApiService.INSTANCE.login(email, password, new HttpCallback<Void>() {
            @Override
            public void onSuccess() {
                onLoginSuccess();
            }

            @Override
            public void onFailure(ApiResponseStatus failureReason) {
                onLoginFailure(failureReason);
            }
        });
    }

    private void onLoginSuccess() {
        Transition progressBarTransition = new Fade();
        progressBarTransition.setDuration(600);
        progressBarTransition.addTarget(R.id.progressBar);
        progressBarTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                Transition progressBarTransition = new Fade();
                progressBarTransition.setDuration(600);
                progressBarTransition.addTarget(R.id.success);
                TransitionManager.beginDelayedTransition((ViewGroup) getWindow().getDecorView().getRootView(), progressBarTransition);
                successText.setVisibility(VISIBLE);
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {

            }
        });
        TransitionManager.beginDelayedTransition((ViewGroup) getWindow().getDecorView().getRootView(), progressBarTransition);
        progressBar.setVisibility(GONE);
    }

    private void onLoginFailure(ApiResponseStatus failureReason) {
        Toast.makeText(this, getErrorResponseText(failureReason), Toast.LENGTH_LONG).show();
        supportFinishAfterTransition();
    }

    private int getErrorResponseText(ApiResponseStatus failureReason) {
        switch (failureReason) {
            case UNAUTHORIZED:
                return R.string.error_unauthorized;
            default:
                return R.string.error_unexpected;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelRequest();
    }

    private void cancelRequest() {
        if (apiCall != null && !apiCall.isCanceled()) {
            apiCall.cancel();
        }
        apiCall = null;
    }
}
