package pgd.dev.artproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import pgd.dev.artproject.Model.NavDrawerItem;
import pgd.dev.artproject.R;

/**
 * Created by Frog-Grammar on 04-Apr-16.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<ViewHolderNavigation> {

    List<NavDrawerItem> listDrawerItem = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> listDrawerItem) {
        this.context = context;
        this.listDrawerItem = listDrawerItem;
        inflater = LayoutInflater.from(context);
    }

    public void delete(int position) {
        listDrawerItem.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolderNavigation onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        ViewHolderNavigation holder = new ViewHolderNavigation(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderNavigation holder, int position) {
        NavDrawerItem navDrawerItem = listDrawerItem.get(position);
        holder.title.setText(navDrawerItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return listDrawerItem.size();
    }
}
