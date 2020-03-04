package course.com.daggercourseapp.di.main;

import course.com.daggercourseapp.network.main.MainApi;
import course.com.daggercourseapp.ui.main.posts.PostsRecyclerAdapter;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi providesMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }


    @Provides
    static PostsRecyclerAdapter providePostsAdapter() {
        return new PostsRecyclerAdapter();
    }
}
