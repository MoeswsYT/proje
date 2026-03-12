# Play Store Benzeri Android App Store + Firebase

Bu proje, Jetpack Compose ile hazırlanmış Google Play Store benzeri bir Android arayüz örneğidir.

## Öne çıkan özellikler
- Üstte arama aksiyonu olan başlık
- Öne çıkan uygulamalar için yatay kart listesi
- Önerilen uygulamalar için dikey liste
- Alt navigasyon sekmeleri (Uygulamalar, Oyunlar, Filmler, Teklifler)
- Firebase Analytics entegrasyonu (ekran açılış eventi)

## Projede Firebase için yapılanlar
- `com.google.gms.google-services` plugin'i eklendi.
- Firebase BoM + Analytics bağımlılığı eklendi.
- `MainActivity` içinde uygulama açılışında örnek `SCREEN_VIEW` eventi loglanıyor.
- Güvenlik için `app/google-services.json` `.gitignore` içine alındı.

## Firebase üzerinde bu uygulamayı açma (adım adım)

### 1) Firebase projesi oluştur
1. https://console.firebase.google.com adresine git.
2. **Add project** ile bir proje aç.

### 2) Android uygulamayı Firebase'e ekle
1. Firebase proje ana ekranında Android simgesine tıkla.
2. **Android package name** alanına şunu yaz: `com.example.playstoreclone`
3. Uygulamayı kaydet.

### 3) `google-services.json` dosyasını projeye koy
1. Firebase'in verdiği `google-services.json` dosyasını indir.
2. Bu dosyayı proje içine şu yola koy:

`app/google-services.json`

> Not: Bu dosya kişisel proje kimlik bilgisi içerir, repoya push etmeyin.

---

## Android Studio olmadan çalıştırma (CLI çözümü)
Evet, çözüm var ✅

### Gerekli araçlar
- JDK 17
- Gradle (sistemde kurulu)
- Android SDK + `platform-tools` (`adb`)
- (Opsiyonel) Emulator veya bağlı fiziksel Android cihaz

### 1) APK üret (komut satırı)
```bash
cd /workspace/proje
scripts/build_apk.sh
```

Bu script şunları yapar:
- `app/google-services.json` dosyasını kontrol eder
- JDK 17 yoksa uyarır/hata verir
- `gradle :app:assembleDebug --no-daemon` ile APK üretir

Çıktı APK:

`app/build/outputs/apk/debug/app-debug.apk`

### 2) APK’yı cihaza yükle
```bash
cd /workspace/proje
scripts/install_apk.sh
```

### 3) Firebase Analytics doğrulama
- Firebase Console → **Analytics** bölümüne gir.
- Uygulamayı açtıktan sonra `screen_view` eventini bir süre sonra görürsün.

---

## Manuel komutlar (script kullanmadan)
```bash
cd /workspace/proje
export JAVA_HOME=/path/to/jdk-17
export PATH="$JAVA_HOME/bin:$PATH"
gradle :app:assembleDebug --no-daemon
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
