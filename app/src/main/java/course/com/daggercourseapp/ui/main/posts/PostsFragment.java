package course.com.daggercourseapp.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import course.com.daggercourseapp.R;
import course.com.daggercourseapp.util.VerticalSpaceItemDecoration;
import course.com.daggercourseapp.viewmodels.ViewModelProviderFactory;
import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";
    private PostsViewModel viewModel;
    private RecyclerView recyclerView;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    PostsRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        initViewModels();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case LOADING:
                        Log.d(TAG, "onChanged: Loading...");
                        break;
                    case SUCCESS:
                        Log.d(TAG, "onChanged: Success...");
                        adapter.setPosts(listResource.data);
                        break;
                    case ERROR:
                        Log.d(TAG, "onChanged: Error..." + listResource.message);
                        break;
                }
            }
        });
    }

    private void initViews(View view) {
        //RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this,
                providerFactory).get(PostsViewModel.class);
    }
}
