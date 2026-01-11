package com.example.harcamatakip.model;

public class Harcama {
    public int id;
    public double tutar;
    public String kategori;
    public String aciklama;
    public long tarih;

    public Harcama(int id, double tutar, String kategori, String aciklama, long tarih) {
        this.id = id;
        this.tutar = tutar;
        this.kategori = kategori;
        this.aciklama = aciklama;
        this.tarih = tarih;
    }
}
