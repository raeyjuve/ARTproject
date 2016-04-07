package pgd.dev.artproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pgd.dev.artproject.Adapter.BeritaAdapter;
import pgd.dev.artproject.Model.BeritaItem;
import pgd.dev.artproject.R;

/**
 * Created by aristio on 4/7/2016.
 */
public class BeritaFragment  extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_berita, container, false);

        RecyclerView recycleView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);
        recycleView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(llm);

        List<BeritaItem> listTest=new ArrayList<BeritaItem>();
        BeritaItem beritaItem;
        for (int i=0;i < 20;i++)
        {
            beritaItem = new BeritaItem();
            beritaItem.BeritaJudul = " Judul Berita " + i;
            beritaItem.BeritaTanggal = "Tanggal "+i;
            beritaItem.BeritaIsi = "Isi " +i;
            listTest.add(beritaItem);
        }

        BeritaAdapter beritaAdapter = new BeritaAdapter(listTest);

        recycleView.setAdapter(beritaAdapter);

        return rootView;
    }
}
