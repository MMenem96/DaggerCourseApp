package course.com.daggercourseapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import course.com.daggercourseapp.models.User;
import course.com.daggercourseapp.ui.auth.AuthResource;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {

    }

    public void authenticateWithId(LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading(null));
            cachedUser.addSource(source, userAuthResource -> {
                cachedUser.setValue(userAuthResource);
                cachedUser.removeSource(source);
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: loggingOut...");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
