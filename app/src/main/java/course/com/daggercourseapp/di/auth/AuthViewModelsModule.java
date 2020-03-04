package course.com.daggercourseapp.di.auth;

import androidx.lifecycle.ViewModel;

import course.com.daggercourseapp.di.ViewModelKey;
import course.com.daggercourseapp.ui.auth.AuthViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

}
