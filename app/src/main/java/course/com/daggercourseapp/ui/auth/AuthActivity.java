package course.com.daggercourseapp.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import course.com.daggercourseapp.R;
import course.com.daggercourseapp.ui.main.MainActivity;
import course.com.daggercourseapp.viewmodels.ViewModelProviderFactory;
import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";
    private ProgressBar progressBar;
    private ImageView ivLogo;
    private AuthViewModel viewModel;
    private EditText etUserId;
    private Button btnLogin;
    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initViewModels();
        initViews();
        setLogo();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observeAuthState().observe(this, userResource -> {
            if (userResource != null) {
                switch (userResource.status) {
                    case LOADING:
                        showProgressBar(true);
                        Log.d(TAG, "onChanged: Loading");
                        break;
                    case AUTHENTICATED:
                        showProgressBar(false);
                        Log.d(TAG, "onChanged: Login Success" + userResource.data.toString());
                        onLoginSuccess();
                        break;
                    case ERROR:
                        Log.d(TAG, "onChanged: Login Failed" + userResource.message);
                        showProgressBar(false);
                        Toast.makeText(this, userResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    case NOT_AUTHENTICATED:
                        showProgressBar(false);
                        break;
                }
            }

        });
    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this,
                providerFactory).get(AuthViewModel.class);
    }

    private void initViews() {
        //Buttons
        btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(this);
        //EditText
        etUserId = findViewById(R.id.user_id_input);
        //ImageViews
        ivLogo = findViewById(R.id.login_logo);
        //ProgressBar
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setLogo() {
        requestManager.load(logo).into(ivLogo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        String userID = etUserId.getText().toString();
        if (TextUtils.isEmpty(userID)) {
            etUserId.setError("Empty Field");
            return;
        } else {
            viewModel.authenticateWithId(Integer.parseInt(userID));
        }
    }

    private void showProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void onLoginSuccess() {
        startActivity(new Intent(AuthActivity.this, MainActivity.class));
        finish();
    }
}
