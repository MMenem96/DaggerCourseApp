package course.com.daggercourseapp.network.main;

import java.util.List;

import course.com.daggercourseapp.models.Post;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {


    @GET("posts")
    Flowable<List<Post>> getPostsFromUser(@Query("userId") int id);
}
