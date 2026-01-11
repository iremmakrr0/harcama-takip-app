package com.example.harcamatakip.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.harcamatakip.R;
import com.example.harcamatakip.db.DatabaseHelper;
import java.util.Calendar;

public class HarcamaEkleActivity extends AppCompatActivity {

    EditText edtTutar, edtAciklama;
    Spinner spinnerKategori;
    Button btnEkle;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harcama_ekle);

        edtTutar = findViewById(R.id.edtTutar);
        edtAciklama = findViewById(R.id.edtAciklama);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnEkle = findViewById(R.id.btnKaydet);

        btnEkle.setText("Ekle");
        btnEkle.setEnabled(false);
        btnEkle.setBackgroundColor(Color.GRAY);

        db = new DatabaseHelper(this);

        // Kategori Spinner ayarları
        String[] kategoriler = {"Yemek", "Ulaşım", "Eğlence", "Fatura", "Diğer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, kategoriler);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        // Tutar girişini dinle (Boşsa veya 0 ise butonu kapa)
        edtTutar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().trim();
                if (!input.isEmpty() && Double.parseDouble(input) > 0) {
                    btnEkle.setEnabled(true);
                    btnEkle.setBackgroundColor(Color.parseColor("#2ECC71"));
                } else {
                    btnEkle.setEnabled(false);
                    btnEkle.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnEkle.setOnClickListener(v -> kaydet());
    }

    private void kaydet() {
        try {
            double tutar = Double.parseDouble(edtTutar.getText().toString());
            String kategori = spinnerKategori.getSelectedItem().toString();
            String aciklama = edtAciklama.getText().toString();
            long tarih = Calendar.getInstance().getTimeInMillis();

            db.harcamaEkle(tutar, kategori, aciklama, tarih);
            Toast.makeText(this, "Harcama eklendi", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Hata oluştu!", Toast.LENGTH_SHORT).show();
        }
    }
}
