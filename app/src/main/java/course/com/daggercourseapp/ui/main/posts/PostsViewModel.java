package course.com.daggercourseapp.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import course.com.daggercourseapp.SessionManager;
import course.com.daggercourseapp.models.Post;
import course.com.daggercourseapp.network.main.MainApi;
import course.com.daggercourseapp.ui.main.Resource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {

    private static final String TAG = "PostsViewModel";
    private SessionManager sessionManager;
    private MainApi mainApi;
    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject

    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        Log.d(TAG, "PostsViewModel: is Running...");
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
    }

    public LiveData<Resource<List<Post>>> observePosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading(null));

            LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())

                            // instead of calling onError, do this
                            .onErrorReturn(throwable -> {
                                Log.e(TAG, "apply: ", throwable);
                                Post post = new Post();
                                post.setId(-1);
                                ArrayList<Post> posts = new ArrayList<>();
                                posts.add(post);
                                return posts;
                            })

                            .map((Function<List<Post>, Resource<List<Post>>>) posts -> {
                                if (posts.size() > 0) {
                                    if (posts.get(0).getId() == -1) {
                                        return Resource.error("Something went wrong", null);
                                    }
                                }
                                return Resource.success(posts);
                            })
                            .subscribeOn(Schedulers.io()));
            posts.addSource(source, listResource -> {
                posts.setValue(listResource);
                posts.removeSource(source);
            });
        }
        return posts;
    }
}
