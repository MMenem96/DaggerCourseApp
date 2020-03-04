package course.com.daggercourseapp.di;

import course.com.daggercourseapp.di.auth.AuthModule;
import course.com.daggercourseapp.di.auth.AuthViewModelsModule;
import course.com.daggercourseapp.di.main.MainFragmentBuildersModule;
import course.com.daggercourseapp.di.main.MainModule;
import course.com.daggercourseapp.di.main.MainViewModelsModules;
import course.com.daggercourseapp.ui.auth.AuthActivity;
import course.com.daggercourseapp.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


//Put all activities
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {AuthViewModelsModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(modules = {MainFragmentBuildersModule.class, MainViewModelsModules.class, MainModule.class})
    abstract MainActivity contributeMainActivity();
}
