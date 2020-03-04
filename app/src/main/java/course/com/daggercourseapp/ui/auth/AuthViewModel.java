package course.com.daggercourseapp.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import course.com.daggercourseapp.SessionManager;
import course.com.daggercourseapp.models.User;
import course.com.daggercourseapp.network.auth.AuthApi;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private final AuthApi authApi;
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        Log.d(TAG, "AuthViewModel: Running...");
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempt to Login...");
        sessionManager.authenticateWithId(queryUserId(userId));
    }


    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams
                .fromPublisher(authApi.authUser(userId)
                        .onErrorReturn(throwable -> {
                            User errorUser = new User();
                            errorUser.setId(-1);
                            return errorUser;
                        })
                        .map((Function<User, AuthResource<User>>) user -> {
                            if (user.getId() == -1) {
                                return AuthResource.error("Could not authenticate", null);
                            }
                            return AuthResource.authenticated(user);
                        })
                        .subscribeOn(Schedulers.io()));
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
