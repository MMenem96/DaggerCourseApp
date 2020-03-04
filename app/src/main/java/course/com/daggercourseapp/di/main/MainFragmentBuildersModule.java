package course.com.daggercourseapp.di.main;

import course.com.daggercourseapp.ui.main.posts.PostsFragment;
import course.com.daggercourseapp.ui.main.profile.ProfileFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostFragment();
}
