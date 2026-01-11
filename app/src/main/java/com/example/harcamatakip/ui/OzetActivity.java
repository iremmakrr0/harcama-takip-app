package com.example.harcamatakip.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.harcamatakip.R;
import com.example.harcamatakip.db.DatabaseHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;

public class OzetActivity extends AppCompatActivity {

    PieChart pieChart;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozet);

        pieChart = findViewById(R.id.pieChart);
        db = new DatabaseHelper(this);

        veriYukleVeGrafikOlustur();
    }

    private void veriYukleVeGrafikOlustur() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        long ayBaslangic = cal.getTimeInMillis();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        long ayBitis = cal.getTimeInMillis();

        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor c = sql.rawQuery(
            "SELECT kategori, SUM(tutar) FROM harcamalar WHERE tarih BETWEEN ? AND ? GROUP BY kategori",
            new String[]{String.valueOf(ayBaslangic), String.valueOf(ayBitis)}
        );

        ArrayList<PieEntry> entries = new ArrayList<>();
        while (c.moveToNext()) {
            String kategori = c.getString(0);
            float toplam = (float) c.getDouble(1);
            entries.add(new PieEntry(toplam, kategori));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Kategori Dağılımı");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Aylık Harcama");
        pieChart.setEntryLabelColor(android.graphics.Color.BLACK);
        pieChart.invalidate();
    }
}
