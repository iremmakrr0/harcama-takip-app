# Harcama Takip Uygulaması

Android tabanlı kişisel finans yönetimi ve harcama takip uygulaması

## Uygulama Hakkında
Bu uygulama, kullanıcıların günlük harcamalarını kategorize ederek kaydetmelerini, takip etmelerini ve aylık harcama dağılımlarını görselleştirmelerini sağlar. Internet bağlantısı gerektirmeden, tamamen offline çalışır.

## Özellikler
- Harcama Ekleme: Tutar, kategori, açıklama ve otomatik tarih kaydı
- Harcama Listeleme: Tüm harcamaları tarih sırasına göre görüntüleme
- Kategori Bazlı Filtreleme: Yemek, Ulaşım, Eğlence, Fatura, Diğer
- Grafik Özeti: Aylık harcama dağılımını pasta grafik ile görselleştirme
- Gerçek Zamanlı Doğrulama: Tutar girişi otomatik kontrol edilir
- Kuruşlu Format: Para birimi formatında gösterim (örn: 500.00 TL)
- Tarih/Saat Kaydı: Her harcama için detaylı zaman bilgisi
- Offline Çalışma: SQLite ile yerel veri saklama

## Teknolojiler
- Platform: Android
- Dil: Java
- Minimum SDK: API 21 (Android 5.0 Lollipop)
- Target SDK: API 33+
- Veritabanı: SQLite
- IDE: Android Studio

## Kullanılan Kütüphaneler

```gradle
dependencies {
    // RecyclerView - Liste görünümü için
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    
    // MPAndroidChart - Grafik görselleştirme için
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    
    // AppCompat - Android destek kütüphanesi
    implementation 'androidx.appcompat:appcompat:1.6.1'
}
```

## Kullanım

### Harcama Ekleme
1. Ana ekranda "Harcama Ekle" butonuna basın
2. Tutarı girin (örn: 250.50)
3. Kategori seçin (Spinner'dan)
4. Açıklama yazın (opsiyonel)
5. "Ekle" butonuna basın

Not: Tutar 0'dan büyük olmalıdır, aksi halde "Ekle" butonu pasif kalır.

### Harcamaları Görüntüleme

- Ana ekran otomatik olarak tüm harcamaları listeler
- En son eklenen harcama en üstte görünür
- Her satırda: kategori, tutar, açıklama, tarih/saat bilgisi bulunur

### Aylık Özet

1. Ana ekranda "Grafik Özeti" butonuna basın
2. Mevcut ayın kategori bazlı dağılımını görüntüleyin
3. Pasta grafik otomatik olarak yüzdelik dilimleri gösterir

## Proje Yapısı

```
app/
├── src/main/java/com/example/harcamatakip/
│   ├── adapter/
│   │   └── HarcamaAdapter.java          # RecyclerView adaptörü
│   ├── db/
│   │   └── DatabaseHelper.java          # SQLite veritabanı yöneticisi
│   ├── model/
│   │   └── Harcama.java                 # Harcama veri modeli
│   └── ui/
│       ├── MainActivity.java            # Ana ekran (liste)
│       ├── HarcamaEkleActivity.java     # Harcama ekleme ekranı
│       └── OzetActivity.java            # Grafik özet ekranı
├── res/
│   └── layout/
│       ├── activity_main.xml            # Ana ekran layout
│       ├── activity_harcama_ekle.xml    # Ekleme ekranı layout
│       └── activity_ozet.xml            # Grafik ekranı layout
└── AndroidManifest.xml
```

## Veritabanı Şeması

Tablo Adı: harcamalar

| Sütun     | Tip     | Açıklama                      |
|-----------|---------|-------------------------------|
| id        | INTEGER | Primary Key (Auto Increment)  |
| tutar     | REAL    | Harcama tutarı                |
| kategori  | TEXT    | Kategori adı                  |
| aciklama  | TEXT    | Harcama açıklaması            |
| tarih     | INTEGER | Unix timestamp (milisaniye)   |

## Geliştirme Notları

### Dinamik Buton Kontrolü

```java
edtTutar.addTextChangedListener(new TextWatcher() {
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
});
```

### Aylık Veri Filtreleme

```java
Calendar cal = Calendar.getInstance();
cal.set(Calendar.DAY_OF_MONTH, 1);
long ayBaslangic = cal.getTimeInMillis();

cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
long ayBitis = cal.getTimeInMillis();

Cursor c = sql.rawQuery(
    "SELECT kategori, SUM(tutar) FROM harcamalar " +
    "WHERE tarih BETWEEN ? AND ? GROUP BY kategori",
    new String[]{String.valueOf(ayBaslangic), String.valueOf(ayBitis)}
);
