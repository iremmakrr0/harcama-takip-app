package com.example.harcamatakip.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.harcamatakip.model.Harcama;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HarcamaAdapter extends RecyclerView.Adapter<HarcamaAdapter.ViewHolder> {

    ArrayList<Harcama> list;

    public HarcamaAdapter(ArrayList<Harcama> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Harcama h = list.get(position);
        
        // Kuruşlu gösterim (500.00 ₺ gibi)
        String formatliTutar = String.format(Locale.getDefault(), "%.2f ₺", h.tutar);
        holder.txt1.setText(h.kategori + " - " + formatliTutar);
        
        // Tarih yanına Saat ve Dakika ekleme
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String tarihSaat = sdf.format(new Date(h.tarih));
        holder.txt2.setText(h.aciklama + " (" + tarihSaat + ")");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;

        ViewHolder(View v) {
            super(v);
            txt1 = v.findViewById(android.R.id.text1);
            txt2 = v.findViewById(android.R.id.text2);
        }
    }
}
