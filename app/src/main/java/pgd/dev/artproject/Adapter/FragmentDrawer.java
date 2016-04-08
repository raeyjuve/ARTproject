package pgd.dev.artproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pgd.dev.artproject.Controller.UserLocalStore;
import pgd.dev.artproject.Model.NavDrawerItem;
import pgd.dev.artproject.R;

/**
 * Created by Frog-Grammar on 04-Apr-16.
 */
public class FragmentDrawer extends Fragment {
    static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;

    private UserLocalStore userLocalStore;
    NavDrawerItem[] itemAll = null;
    List<NavDrawerItem> listDrawer = new ArrayList<NavDrawerItem>();

    // add menu item
    NavDrawerItem[] itemAuth = {
            new NavDrawerItem(1, "Profil", "pgd.dev.artproject.Fragments.ProfileFragment", 1),
            new NavDrawerItem(2, "Beranda", "pgd.dev.artproject.Fragments.HomeFragment", 0),
            new NavDrawerItem(3, "Berita", "pgd.dev.artproject.Fragments.BeritaFragment", 1),
            new NavDrawerItem(4, "Cek kredit", "pgd.dev.artproject.Fragments.CekKreditFragment", 1),
            new NavDrawerItem(5, "Simulasi", "pgd.dev.artproject.Fragments.SimulasiFragment", 0),
            new NavDrawerItem(6, "Lelang", "pgd.dev.artproject.Fragments.LelangFragment", 1),
            new NavDrawerItem(7, "Maps", "pgd.dev.artproject.Fragments.MapsFragment", 0),
            new NavDrawerItem(8, "Pengaturan", "pgd.dev.artproject.Fragments.PengaturanFragment", 1),
            new NavDrawerItem(9, "Logout", "pgd.dev.artproject.Fragments.HomeFragment", 1)
    };

    NavDrawerItem[] itemNoAuth = {
            new NavDrawerItem(2, "Beranda", "pgd.dev.artproject.Fragments.HomeFragment", 0),
            new NavDrawerItem(5, "Simulasi", "pgd.dev.artproject.Fragments.SimulasiFragment", 0),
            new NavDrawerItem(7, "Maps", "pgd.dev.artproject.Fragments.MapsFragment", 0),
            new NavDrawerItem(10, "Login", "pgd.dev.artproject.Fragments.LoginFragment", 0)
    };

    public FragmentDrawer() {
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public List<NavDrawerItem> getList() {
        listDrawer.clear();
        for (NavDrawerItem item : itemAll) {
            listDrawer.add(item);
        }
        return listDrawer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create and store user
        userLocalStore = new UserLocalStore(getContext());

        if (authenticate() == true) {
            itemAll = itemAuth;
        } else {
            itemAll = itemNoAuth;
        }
    }

    public void updateDrawer() {
        listDrawer.clear();
        if (authenticate() == true) {
            itemAll = itemAuth;
        } else {
            itemAll = itemNoAuth;
        }

        for (NavDrawerItem item : itemAll) {
            listDrawer.add(item);
        }

        adapter = new NavigationDrawerAdapter(getActivity(), listDrawer);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
//        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        adapter = new NavigationDrawerAdapter(getActivity(), getList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int id) {
                NavDrawerItem item = listDrawer.get(id);
                drawerListener.onDrawerItemSelected(view, item);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int id) {
            }
        }));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
//        public void onDrawerItemSelected(View view, int position);
        public void onDrawerItemSelected(View view, NavDrawerItem item);
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedid();
    }
}
