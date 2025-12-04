# Manufacturing ERP Module - Team 4

Sistem ERP modul Manufaktur (Lite) berbasis Web API yang modern, dirancang untuk memantau alur produksi dari Work Order hingga penyelesaian barang jadi. Proyek ini mengimplementasikan arsitektur Clean Architecture dengan Backend Kotlin dan Database PostgreSQL.

## Anggota Kelompok (Team 4)
* **Lukita Falensia Marjan** - 224443009 - Lead Developer & System Architect
* **Fauzan Nafidza Ahmad** - 224443005 - Database Specialist
* **Ilham Nur Fiqri** - 224443008 - QA & Documentation

## Fitur Unggulan
* **Real-time Dashboard:** Pemantauan status produksi dan stok gudang secara langsung.
* **Work Order Lifecycle:** Manajemen status produksi (Pending -> In Progress -> Completed) dengan validasi ketat.
* **PostgreSQL Integration:** Penyimpanan data persisten yang tangguh dan skalabel.
* **Modern UI:** Antarmuka pengguna berbasis Tailwind CSS yang responsif dan estetis.
* **RESTful API:** Arsitektur backend yang terpisah dan modular menggunakan Ktor.

## Teknologi
* **Language:** Kotlin (JDK 24)
* **Framework:** Ktor 3.0 (Server), Exposed (ORM)
* **Database:** PostgreSQL 16
* **Frontend:** HTML5, Tailwind CSS, Vanilla JS

## Cara Menjalankan
1.  Pastikan **PostgreSQL** sudah berjalan dan database `erp_manufacturing` telah dibuat.
2.  Konfigurasi koneksi database di `src/main/kotlin/.../database/DatabaseFactory.kt`.
3.  Jalankan file `src/main/kotlin/.../app/Main.kt` via IntelliJ IDEA.
4.  Akses dashboard melalui browser: `http://localhost:8081`

## Pengujian
Skenario uji mencakup pembuatan Work Order baru, validasi stok bahan baku (simulasi), dan transisi status produksi. Bukti pengujian lengkap terlampir dalam dokumen laporan.
