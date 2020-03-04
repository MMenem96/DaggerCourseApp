package course.com.daggercourseapp.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import course.com.daggercourseapp.SessionManager;
import course.com.daggercourseapp.models.User;
import course.com.daggercourseapp.ui.auth.AuthResource;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        Log.d(TAG, "ProfileViewModel: ViewModel is Running...");
        this.sessionManager = sessionManager;
    }


    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }

}
