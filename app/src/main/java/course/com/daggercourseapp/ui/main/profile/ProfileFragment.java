package course.com.daggercourseapp.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import course.com.daggercourseapp.R;
import course.com.daggercourseapp.models.User;
import course.com.daggercourseapp.viewmodels.ViewModelProviderFactory;
import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private TextView tvUserEmail, tvUserName, tvUserWebsite;

    private ProfileViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this,
                providerFactory).get(ProfileViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        initViewModels();
        subscribeObservers();
    }

    private void initViews(View view) {
        //TextViews
        tvUserEmail = view.findViewById(R.id.email);
        tvUserWebsite = view.findViewById(R.id.website);
        tvUserName = view.findViewById(R.id.username);
    }

    private void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case AUTHENTICATED:
                        setUserDetails(userAuthResource.data);
                        break;
                    case ERROR:
                        setErrorDetails(userAuthResource.message);
                        break;
                }
            }
        });
    }

    private void setErrorDetails(String errorMessage) {
        tvUserName.setText("Error");
        tvUserEmail.setText(errorMessage);
        tvUserWebsite.setText("Error");
    }

    private void setUserDetails(User user) {
        tvUserName.setText(user.getUsername());
        tvUserEmail.setText(user.getEmail());
        tvUserWebsite.setText(user.getWebsite());
    }


}
