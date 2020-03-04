package course.com.daggercourseapp.di.main;


import androidx.lifecycle.ViewModel;

import course.com.daggercourseapp.di.ViewModelKey;
import course.com.daggercourseapp.ui.main.posts.PostsViewModel;
import course.com.daggercourseapp.ui.main.profile.ProfileViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModules {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel postsViewModel);
}
