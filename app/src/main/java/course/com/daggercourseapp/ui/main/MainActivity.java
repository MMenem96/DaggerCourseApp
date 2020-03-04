package course.com.daggercourseapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import course.com.daggercourseapp.BaseActivity;
import course.com.daggercourseapp.R;
import course.com.daggercourseapp.SessionManager;
import course.com.daggercourseapp.ui.auth.AuthActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        //DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        //NavigationView
        navigationView = findViewById(R.id.nav_view);
        //NavControllers
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_log_out:
                sessionManager.logOut();
                navToLoginScreen();
                return true;

            case android.R.id.home:
                if (drawerLayout.isOpen()) {
                    drawerLayout.close();
                    return true;
                } else return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navToLoginScreen() {
        startActivity(new Intent(MainActivity.this, AuthActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main, true).build();
                Navigation.findNavController(this, R.id.nav_host_fragment_container).navigate(R.id.profileScreen, null, navOptions);
                break;
            case R.id.action_post:
                if (isValidDestination(R.id.postsScreen))
                    Navigation.findNavController(this, R.id.nav_host_fragment_container).navigate(R.id.postsScreen);
                break;
        }

        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.
                        findNavController(this, R.id.nav_host_fragment_container),
                drawerLayout);
    }


    public boolean isValidDestination(int destination) {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment_container).getCurrentDestination().getId();
    }
}
