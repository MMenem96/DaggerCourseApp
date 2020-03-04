package course.com.daggercourseapp.di.auth;

import course.com.daggercourseapp.network.auth.AuthApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @Provides
    static AuthApi providesAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }
}
