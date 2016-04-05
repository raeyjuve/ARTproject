package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pgd.dev.artproject.R;

/**
 * Created by Frog-Grammar on 04-Apr-16.
 */
public class ViewHolderNavigation extends RecyclerView.ViewHolder {
    TextView title, tag;

    public ViewHolderNavigation(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        tag = (TextView) itemView.findViewById(R.id.tag);
    }
}
