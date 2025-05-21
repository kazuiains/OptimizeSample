
# Optimize Sample

aplikasi dengan menerapkan clean architecture di jetpack compose. dan berupa contoh penerapan yang ideal




## Struktur Project

sebelum memasuki projek, harap baca dokumentasi ini untuk menyatukan presepsi agar mencapai project yg clean dan terstruktur.

dalam project ini menerapkan clean architecture. dalam clean architecture terdapat 3 layer sebagai berikut:

    1. Data layer
        - Data Sources
        - Models
        - repositories

    2. Domain layer
        - Use Cases
        - Entities
        - Repositories

    3. Presentation Layer
        - Data
        - Component
            - UI
            - State Holder
            - Handler
        - screen
            - UI
            - ViewModel

di atas merupakan struktur folder dari clean architecture, dimasing masing layer terdapat folder yang akan kita bahas di bawah ini.

## Data Layer

Menyediakan mekanisme untuk mengambil dan menyimpan data. baik itu local atau remote.

**Data Sources** : Berinteraksi dengan sumber data eksternal (misalnya, database lokal dan API).

**Model** : Struktur data / data class yang digunakan untuk mentransfer data antara Data Source dan Repository Implementation.

**Repository (Implementation)** : Mengimplementasikan Repository Interface yang didefinisikan di Domain Layer. Ia bertanggung jawab untuk mengambil data dari Data Source, memetakan antara Model (Data Layer) dan Entity (Domain Layer), dan menyediakan data ke Domain Layer.

**Interaksi** : Repository (Implementation) memanggil Data Source untuk berinteraksi dengan sumber data. Mengimplementasikan interface dari Domain Layer.


## Domain Layer

Berisi logika bisnis inti aplikasi. Ini adalah lapisan yang paling stabil dan independen dari framework atau implementasi eksternal.

**Use Case** : Mengenkapsulasi logika bisnis spesifik. Use Case berinteraksi dengan Repository (Interface) untuk mengambil atau memanipulasi data yang relevan untuk skenario bisnis tertentu.

**Entity** : Objek bisnis yang mewakili data inti aplikasi.

**Repository (Interface)** : Mendefinisikan interface untuk mengakses data. Interface ini tidak memiliki implementasi di Domain Layer.

**Interaksi** : Use Case memanggil Repository Interface untuk berinteraksi dengan lapisan data. Domain Layer tidak memiliki dependensi pada lapisan Data Layer.


## Presentation Layer

Menampilkan data kepada pengguna dan meneruskan tindakan pengguna ke domain layer.

**Data** : Data class yang digunakan merepresentasikan data yang dibutuhkan Presentation Layer saja.

**Component** : merupakan tampilan terkecil yang dapat digunakan ulang.

**Screen** : tampilan utuh yang membentuk suatu halaman.

**UI** : merupakan file yang berisi composable function untuk menyusun tampilan.

**State Holder** : kelas kustom yang berfungsi untuk menyimpan state UI yang kompleks dan mungkin melibatkan logika UI. Kelas ini bisa saja di-remember atau tidak, tetapi fokusnya adalah sebagai wadah untuk mengelola state yang lebih dari sekadar satu nilai sederhana.

**Handler** : untuk menangani atau memproses suatu tindakan, kejadian, atau permintaan tertentu sesuai dengan kontrak yang didefinisikan oleh interface.

**ViewModel** : menyiapkan dan mengelola data yang akan ditampilkan oleh UI, serta menangani state dan logika UI. selain itu viewmodel juga mengelola alur data antara UI dan Use.

**Interaksi** : Berinteraksi dengan Use Case di Domain Layer untuk mendapatkan data atau melakukan tindakan bisnis. **Clean Architecture tidak membolehkan kita untuk memanggil langsung repository atau Data Source di viewmodel. kita wajib memakai use case.** akan tetapi dalam project ini kita memiliki fleksibilitas. use case dibuat ketika terdapat logic bisnis yang sangat spesifik dan detail. **jika hanya memanggil API atau Local data tanpa ada bisnis logic kita diperbolehkan memanggil Repository dan tetap tidak diperbolehkan memanggil Data Source.**


## Viewmode & State Holder dalam state dan logic UI

#### **State Holder**

**Tujuan** : Menyimpan dan mengelola status UI untuk satu komponen composable atau bagian layar yang spesifik.

**Karakteristik** : 
    
    - Berupa kelas biasa yang menyimpan status menggunakan mutableStateOf atau MutableStateFlow. 

    - Bertanggung jawab untuk logika UI yang berkaitan dengan status komponen tersebut
    
    - Tidak terikat langsung pada siklus hidup Android seperti ViewModel.
    
    - Disediakan ke composable melalui remember atau rememberSaveable untuk mempertahankan status selama konfigurasi berubah.
    
    
**Kapan Menggunakan** :
   
    - Untuk mengelola status UI lokal dari sebuah composable yang kompleks, terutama jika status tersebut melibatkan logika UI spesifik.

    - Ketika Anda ingin meningkatkan reusability dan testability dari composable dengan memisahkan logika status dari deklarasi UI.
    
    - Untuk status UI yang tidak perlu bertahan selama seluruh siklus hidup layar atau aplikasi.

#### **ViewModel**

**Tujuan** : Menyimpan dan mengelola status UI yang terkait dengan layar atau fitur yang lebih besar, serta menyediakan akses ke data dan logika bisnis.

**Karakteristik** : 
    
    - Kelas yang disediakan oleh Android Jetpack (androidx.lifecycle.ViewModel).
    
    - Terikat pada siklus hidup Activity atau Fragment (atau NavBackStackEntry dalam Compose Navigation).
    
    - Bertahan selama perubahan konfigurasi (misalnya, rotasi layar).
    
    - Mendapatkan dan memproses data dari lapisan domain (Use Case/Repository).
    
    - Mengekspos status UI sebagai StateFlow atau LiveData untuk diamati oleh composable
    
    - Bertanggung jawab untuk menangani peristiwa UI dan memicu pembaruan status.
    
    
**Kapan Menggunakan** :
   
    - Untuk mengelola status UI yang kompleks dan perlu bertahan selama perubahan konfigurasi.
    
    - Sebagai perantara antara UI (composable) dan lapisan domain (Use Case/Repository).
    
    - Untuk logika bisnis yang terkait dengan tampilan layar atau fitur.
    
    - Untuk berbagi status di antara beberapa composable dalam satu layar.


#### **Singkatnya:**

Gunakan **State Holder** untuk mengelola status UI lokal dan logika UI spesifik dari composable individual.

Gunakan **ViewModel** untuk mengelola status UI tingkat layar atau fitur, bertahan selama perubahan konfigurasi, dan menghubungkan UI dengan lapisan domain.