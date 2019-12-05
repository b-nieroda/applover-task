package com.nieroda.app.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;

import com.nieroda.app.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationUtil {
    private static final String HAS_CLEAN_ERROR_WATCHER = "HAS_CLEAN_ERROR_WATCHER";

    public static boolean validate(final TextInputLayout emailView, final TextInputLayout passwordView, String email, String password) {

        if (TextUtils.isEmpty(email)) {
            emailView.requestFocus();
            emailView.setError(emailView.getContext().getString(R.string.error_field_required));
            emailView.setErrorEnabled(true);
            if (emailView.getTag() == null) {
                emailView.setTag(HAS_CLEAN_ERROR_WATCHER);
                emailView.getEditText().addTextChangedListener(new CleanErrorWatcher(emailView));
            }
            return false;
        }
        if (!isEmailValid(email)) {
            emailView.requestFocus();
            emailView.setError(emailView.getContext().getString(R.string.error_invalid_email));
            emailView.setErrorEnabled(true);
            if (emailView.getTag() == null) {
                emailView.setTag(HAS_CLEAN_ERROR_WATCHER);
                emailView.getEditText().addTextChangedListener(new CleanErrorWatcher(emailView));
            }
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordView.requestFocus();
            passwordView.setError(passwordView.getContext().getString(R.string.error_field_required));
            passwordView.setErrorEnabled(true);
            if (passwordView.getTag() == null) {
                passwordView.setTag(HAS_CLEAN_ERROR_WATCHER);
                passwordView.getEditText().addTextChangedListener(new CleanErrorWatcher(passwordView));
            }
            return false;
        }
        return true;
    }

    private static boolean isEmailValid(final String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static class CleanErrorWatcher implements TextWatcher {
        private final TextInputLayout view;

        CleanErrorWatcher(final TextInputLayout view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            view.setError(null);
            view.setErrorEnabled(false);
            view.getEditText().removeTextChangedListener(this);
            view.setTag(null);
        }
    }
}
