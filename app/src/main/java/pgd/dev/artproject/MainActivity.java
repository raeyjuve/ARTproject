package pgd.dev.artproject;

import android.app.ProgressDialog;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        addMenu();
        initComponent();
    }

    public void addMenu() {

    }

    public void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        drawerFragment = (FragmentDrawer) fragmentManager.findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        userLocalStore = new UserLocalStore(getApplicationContext());

        setDafaultFragment();
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

    private void setDafaultFragment() {
        Fragment fragment = new HomeFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle("Beranda");
        }
    }

//    private void itemMenuChange(int menuId) {
//        Fragment fragment = null;
//        String title = "ARTproject";
//        if (menuId == 0) {
//            fragment = new HomeFragment();
//        } else if (menuId == 1) {
//            fragment = new CekKreditFragment();
//        } else if (menuId == 2) {
//            fragment = new MapsFragment();
//        } else if (menuId == 3) {
//            fragment = new ProfileFragment();
//        } else if (menuId == 4) {
//            isLogout = true;
////            fragment = new LogoutFragment();
//        }
//
//        if (fragment != null) {
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            fragmentTransaction.commit();
//            getSupportActionBar().setTitle(title);
//        }
//
//        if (isLogout == true) {
//            logout();
//        }
//    }

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
//                    userLocalStore.clearUsereData();
//                    userLocalStore.setUserLoggedIn(false);
//                    updateGenerateDrawer();
                }
                Class<?> obj = Class.forName(menu.getClassName());
                fragment = (Fragment) obj.newInstance();
                title = menu.getTitle();
            }
        } catch (Exception e) {
            e.printStackTrace();
            fragment = new HomeFragment();
        }

        Log.i("Menu Yang di Pilih : ", menu.getClassName().toUpperCase());

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

//    private void displayViewOld(NavDrawerItem item) {
//        Fragment fragment = null;
//        String title = getString(R.string.app_name);
//        try {
//            if (item != null) {
//                /**
//                 *  9 adalah id dari item fragments.LogoutFragment
//                 *  10 adalah id dari item pgd.dev.artproject.LoginActivity
//                */
//                if (9 == item.getId() || 10 == item.getId()) {
//                    if (9 == item.getId()) {
//                        // Fungsi Logout
//                        logout();
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivityForResult(intent, 0);
//                    }
//                } else {
//                    Class<?> obj = Class.forName(item.getClassName());
//                    fragment = (Fragment) obj.newInstance();
//                }
//                title = item.getTitle();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            fragment = new HomeFragment();
//        }
//
//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            fragmentTransaction.commit();
//
//            getSupportActionBar().setTitle(title);
//        }
//    }

    private void logout() {
//        logoutProccess();
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
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    private void logoutProccess() {
        userLocalStore.clearUsereData();
        userLocalStore.setUserLoggedIn(false);
        updateGenerateDrawer();
    }

    private void logoutProccessDua() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0) {
//            userLocalStore.clearUsereData();
//            userLocalStore.setUserLoggedIn(false);
//            updateGenerateDrawer();
//            this.finish();
//        }
//    }

    public void updateGenerateDrawer(){
        drawerFragment.updateDrawer();
    }

}