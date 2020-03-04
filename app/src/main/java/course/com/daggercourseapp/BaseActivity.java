package course.com.daggercourseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import course.com.daggercourseapp.ui.auth.AuthActivity;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscirbeObservers();
    }

    private void subscirbeObservers() {
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case LOADING:
//                        showProgressBar(true);
//                        Log.d(TAG, "onChanged: Loading");
                        break;
                    case AUTHENTICATED:
//                        showProgressBar(false);
//                        Log.d(TAG, "onChanged: Login Success" + userResource.data.toString());
                        break;
                    case ERROR:
//                        Log.d(TAG, "onChanged: Login Failed" + userResource.message);
//                        showProgressBar(false);
//                        Toast.makeText(this, userResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    case NOT_AUTHENTICATED:
                        navLoginScreen();
                        break;
                }
            }

        });
    }

    private void navLoginScreen() {
        startActivity(new Intent(BaseActivity.this, AuthActivity.class));
        finish();
    }
}
