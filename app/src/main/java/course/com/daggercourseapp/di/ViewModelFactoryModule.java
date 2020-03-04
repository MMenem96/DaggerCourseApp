package course.com.daggercourseapp.di;

import androidx.lifecycle.ViewModelProvider;

import course.com.daggercourseapp.viewmodels.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

}
