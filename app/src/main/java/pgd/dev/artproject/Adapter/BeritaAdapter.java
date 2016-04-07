package pgd.dev.artproject.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pgd.dev.artproject.Fragments.HomeFragment;
import pgd.dev.artproject.Model.BeritaItem;
import pgd.dev.artproject.R;

/**
 * Created by aristio on 4/5/2016.
 */
public class BeritaAdapter extends RecyclerView.Adapter<ViewHolderBerita> {
    private List<BeritaItem> contactList;
    public String tag = "LOOOLLLL.....";

    public BeritaAdapter(List<BeritaItem> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ViewHolderBerita onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_berita, viewGroup, false);
        return new ViewHolderBerita(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderBerita holder, int position) {
        final BeritaItem dataBerita = contactList.get(position);
        holder.vBtnView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                Log.i(tag, "click nii Looolllll..!!!" + dataBerita.BeritaJudul);
//                v.getContext().startActivity(new Intent(v.getContext(),MainActivity.class));

                Intent intent = new Intent(v.getContext(), HomeFragment.class);
                intent.putExtra("judul", dataBerita.BeritaJudul);
                intent.putExtra("tanggal", dataBerita.BeritaTanggal);
                intent.putExtra("isi", dataBerita.BeritaIsi);

                v.getContext().startActivity(intent);
            }
        });
        holder.vBeritaJudul.setText(dataBerita.BeritaJudul);
        holder.vBeritaTanggal.setText(dataBerita.BeritaTanggal);
        holder.vBeritaIsi.setText(dataBerita.BeritaIsi);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

