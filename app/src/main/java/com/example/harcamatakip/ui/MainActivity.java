package com.example.harcamatakip.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.harcamatakip.R;
import com.example.harcamatakip.adapter.HarcamaAdapter;
import com.example.harcamatakip.db.DatabaseHelper;
import com.example.harcamatakip.model.Harcama;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<Harcama> liste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);

        verileriYukle();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HarcamaAdapter(liste));
    }

    @Override
    protected void onResume() {
        super.onResume();
        liste.clear();
        verileriYukle();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void verileriYukle() {
        Cursor c = db.tumHarcamalar();
        while (c.moveToNext()) {
            liste.add(new Harcama(
                c.getInt(0),
                c.getDouble(1),
                c.getString(2),
                c.getString(3),
                c.getLong(4)
            ));
        }
    }

    public void harcamaEkleAc(View v) {
        startActivity(new Intent(this, HarcamaEkleActivity.class));
    }

    public void ozetAc(View v) {
        startActivity(new Intent(this, OzetActivity.class));
    }
}
