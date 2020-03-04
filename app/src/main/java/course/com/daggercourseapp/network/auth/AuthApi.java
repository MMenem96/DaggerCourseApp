package course.com.daggercourseapp.network.auth;

import course.com.daggercourseapp.models.User;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {

    @GET("users/{id}")
    Flowable<User> authUser(@Path("id") int id);


}
