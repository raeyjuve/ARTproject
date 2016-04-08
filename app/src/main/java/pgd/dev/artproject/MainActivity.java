package pgd.dev.artproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pgd.dev.artproject.Adapter.FragmentDrawer;
import pgd.dev.artproject.Controller.UserLocalStore;
import pgd.dev.artproject.Fragments.HomeFragment;
import pgd.dev.artproject.Model.NavDrawerItem;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Toolbar toolbar;
    FragmentDrawer drawerFragment;
    FragmentManager fragmentManager;
    boolean isLogout = false;
    UserLocalStore userLocalStore;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
    }

    public void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        drawerFragment = (FragmentDrawer) fragmentManager.findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        // change Title by fragmentID
//        addBackStackListener(fragmentManager);

        userLocalStore = new UserLocalStore(getApplicationContext());

        executeFragment(new HomeFragment(), "Home Fragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, NavDrawerItem item) {
        displayView(item);
    }

    private void displayView(NavDrawerItem menu) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        try {
            if (menu != null) {
                if (9 == menu.getId()) {
                    // Fungsi Logout
                    logout();
                    title = "Home";
                } else {

                    title = menu.getTitle();
                }
                Class<?> obj = Class.forName(menu.getClassName());
                fragment = (Fragment) obj.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            fragment = new HomeFragment();
        }

        executeFragment(fragment, title);
    }

    public void executeFragment(Fragment fragment, String title) {
        if (fragment != null) {
            String backStackName = fragment.getClass().getName();

            FragmentManager fragmentManager = getSupportFragmentManager();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
            if (!fragmentPopped && fragmentManager.findFragmentByTag(backStackName) == null) {
                fragmentManager.popBackStackImmediate(null, 0);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
//                fragmentTransaction.addToBackStack(String.valueOf(fragmentManager.findFragmentById(R.id.container_body)));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            getSupportActionBar().setTitle(title);
        }
        Log.i("Menu Yang di Pilih ", fragment.getClass().getName().toUpperCase());
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (currentFragment instanceof HomeFragment) {
            checkDoubleBackPress();
        } else {
            executeFragment(new HomeFragment(), "Home");
        }
    }

    private void checkDoubleBackPress() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }

    private void logout() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging Out...");
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        logoutProccess();
                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    private void logoutProccess() {
        userLocalStore.clearUsereData();
        userLocalStore.setUserLoggedIn(false);
        updateGenerateDrawer();
    }

    public void updateGenerateDrawer(){
        drawerFragment.updateDrawer();
    }

}