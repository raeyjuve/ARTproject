package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pgd.dev.artproject.R;

/**
 * Created by aristio on 4/7/2016.
 */
public class ViewHolderBerita extends RecyclerView.ViewHolder {
    protected TextView vBeritaJudul;
    protected TextView vBeritaTanggal;
    protected TextView vBeritaIsi;
    protected Button vBtnView;
    public View view;

    public ViewHolderBerita(View itemView) {
         super(itemView);
         view = itemView;
         vBeritaJudul = (TextView) itemView.findViewById(R.id.txtJudul);
         vBeritaTanggal = (TextView) itemView.findViewById(R.id.txtTanggal);
         vBeritaIsi = (TextView) itemView.findViewById(R.id.txtIsi);
         vBtnView = (Button) itemView.findViewById(R.id.btnView);
    }
}
